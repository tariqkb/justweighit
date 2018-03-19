package com.justweighit.search.units;

import com.justweighit.units.Unit;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.apache.lucene.search.*;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery.Builder;
import org.apache.lucene.search.spell.LuceneLevenshteinDistance;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.search.spell.StringDistance;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.AttributeFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class UnitSearcher {
	
	private final Logger logger = Logger.getLogger(UnitSearcher.class.getName());
	private final Analyzer analyzer;
	private final Directory index;
	private QueryParser queryParser;
	
	public UnitSearcher() {
		try {
			analyzer = new EnglishAnalyzer();
			index = new RAMDirectory();
			
			queryParser = new QueryParser("unit", analyzer);
			queryParser.setDefaultOperator(Operator.OR);
			
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			
			IndexWriter w = new IndexWriter(index, config);
			addDoc(w, "teaspoon", Unit.teaspoon);
			addDoc(w, "tsp", Unit.teaspoon);
			addDoc(w, "tablespoon", Unit.tablespoon);
			addDoc(w, "tbsp", Unit.tablespoon);
			addDoc(w, "cup", Unit.cup);
			addDoc(w, "cup", Unit.cup);
			addDoc(w, "oz", Unit.ounces);
			addDoc(w, "ounces", Unit.ounces);
			addDoc(w, "fluid ounces", Unit.flOz);
			addDoc(w, "fluid oz", Unit.flOz);
			addDoc(w, "fl oz", Unit.flOz);
			addDoc(w, "floz", Unit.flOz);
			addDoc(w, "g", Unit.grams);
			addDoc(w, "grams", Unit.grams);
			w.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private String preparse(String query) throws IOException {
		logger.info("Query before: " + query);
		
		StandardTokenizer tokenizer = new StandardTokenizer(AttributeFactory.DEFAULT_ATTRIBUTE_FACTORY);
		tokenizer.setReader(new StringReader(query));
		tokenizer.reset();
		
		List<String> tokens = new ArrayList<>();
		CharTermAttribute attr = tokenizer.addAttribute(CharTermAttribute.class);
		
		String previousTerm = null;
		while (tokenizer.incrementToken()) {
			String term = attr.toString();
			tokens.add(term + "~");
			
			if (previousTerm != null) {
//				tokens.add("(" + previousTerm + " " + term + ")~");
			}
			
			previousTerm = term;
		}
		
		String newQuery = StringUtils.join(tokens, " ");
		logger.info("Query after: " + newQuery);
		
		return newQuery;
	}
	
	public Query span(String query, float threshold, List<String> spellCheckedStrings) throws IOException {
		StandardTokenizer tokenizer = new StandardTokenizer(AttributeFactory.DEFAULT_ATTRIBUTE_FACTORY);
		tokenizer.setReader(new StringReader(query));
		tokenizer.reset();
		
		CharTermAttribute attr = tokenizer.addAttribute(CharTermAttribute.class);
		
		List<String> terms = new ArrayList<>();
		String prev = null;
		while (tokenizer.incrementToken()) {
			String term = attr.toString();
			
			terms.add(term);
			
			if (prev != null) {
				terms.add(prev + " " + term);
			}
			
			prev = term;
		}
		
		SpellChecker spellChecker = null;
		List<String> spellCheckedTerms = new ArrayList<>();
		try {
			for (String term : terms) {
				PlainTextDictionary dictionary = new PlainTextDictionary(getClass().getResourceAsStream("/unitDictionary.txt"));
				StringDistance sd = new LuceneLevenshteinDistance();
				spellChecker = new SpellChecker(index, sd);
				spellChecker.indexDictionary(dictionary, new IndexWriterConfig(analyzer), true);
				
				String[] strings = spellChecker.suggestSimilar(term, 5, threshold);
				
				if (strings.length > 0) {
					logger.info("Suggesting '" + Arrays.toString(strings) + "' for " + term);
					spellCheckedTerms.add(strings[0]);
					spellCheckedStrings.add(term);
				} else {
					logger.info("Not suggesting anything for " + term);
					spellCheckedTerms.add(term);
				}
			}
			
			if (spellCheckedTerms.size() < 2) {
				return new FuzzyQuery(new Term("unit", spellCheckedTerms.size() == 1 ? spellCheckedTerms.get(0) : ""));
			} else {
				Builder builder = new Builder().setMinimumNumberShouldMatch(1);
				spellCheckedTerms.stream()
					.map(term -> new FuzzyQuery(new Term("unit", term), 1))
					.forEach(q -> builder.add(q, Occur.SHOULD));
				return builder.build();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (spellChecker != null) {
				try {
					spellChecker.close();
				} catch (IOException ignored) {}
			}
		}
	}
	
	public List<UnitWithMatchedString> run(String queryStr) {
		List<UnitWithMatchedString> units = run(queryStr, 0.8f);
		
		if (units.isEmpty()) {
			return run(queryStr, 0.5f);
		}
		
		return units;
	}
	
	private List<UnitWithMatchedString> run(String queryStr, float threshold) {
		
		IndexReader reader = null;
		try {
			List<String> spellCheckedTerms = new ArrayList<>();
			Query q = span(QueryParser.escape(queryStr), threshold, spellCheckedTerms);
			logger.info("Query: " + q);
			
			int hitsPerPage = 10;
			reader = DirectoryReader.open(index);
			
			IndexSearcher searcher = new IndexSearcher(reader);
			TopDocs docs = searcher.search(q, hitsPerPage);
			ScoreDoc[] hits = docs.scoreDocs;
			
			return Arrays.stream(hits).map(hit -> {
				try {
					List<Term> matchedTerms = new ArrayList<>();
					getHitTerms(q, searcher, hit.doc, matchedTerms, new ArrayList<>());
					
					List<String> matched = matchedTerms.stream().map(Term::text).collect(Collectors.toList());
					matched.addAll(spellCheckedTerms);
					
					return new UnitWithMatchedString(Unit.valueOf(searcher.doc(hit.doc).get("id")), matched);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}).collect(Collectors.toList());
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ignored) {}
			}
		}
	}
	
	private void getHitTerms(Query query, IndexSearcher searcher, int docId, List<Term> hitTerms, List<Term> rest) throws IOException {
		if (query instanceof TermQuery) {
			if (searcher.explain(query, docId).isMatch()) {
				hitTerms.add(((TermQuery) query).getTerm());
			} else {
				rest.add(((TermQuery) query).getTerm());
				return;
			}
		}
		
		if (query instanceof BooleanQuery) {
			List<BooleanClause> clauses = ((BooleanQuery) query).clauses();
			if (clauses == null) { return; }
			
			for (BooleanClause bc : clauses) {
				getHitTerms(bc.getQuery(), searcher, docId, hitTerms, rest);
			}
			return;
		}
		
		if(query instanceof BoostQuery) {
			getHitTerms(((BoostQuery) query).getQuery(), searcher, docId, hitTerms, rest);
		}
		
		if (query instanceof MultiTermQuery) {
//			if (!(query instanceof FuzzyQuery)) {
				((MultiTermQuery) query).setRewriteMethod(MultiTermQuery.SCORING_BOOLEAN_REWRITE);
//			}
			
			getHitTerms(query.rewrite(searcher.getIndexReader()), searcher, docId, hitTerms, rest);
		}
	}
	
	private static void addDoc(IndexWriter w, String unitName, Unit unit) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("unit", unitName, Field.Store.YES));
		doc.add(new StringField("id", unit.name(), Field.Store.YES));
		w.addDocument(doc);
	}
	
	public static void debugHits(String query) throws IOException, ParseException {
		List<UnitWithMatchedString> hits = new UnitSearcher().run(query);
		
		System.out.println(query + " | Found " + hits.size() + " hits");
		for (UnitWithMatchedString hit : hits) {
			System.out.println(hit.getUnit());
		}
		System.out.println();
	}
	
	public static void main(String[] args) throws IOException, ParseException {
//		debugHits("floz");
//		debugHits("fl od");
//		debugHits("flsod");
		
		LuceneLevenshteinDistance sd = new LuceneLevenshteinDistance();
		System.out.println(sd.getDistance("fluid oz", "fluid ounces"));
		System.out.println(sd.getDistance("tps", "tsp"));
		System.out.println(sd.getDistance("tps", "tbsp"));
	}
}
