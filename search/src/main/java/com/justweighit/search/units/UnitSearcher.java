package com.justweighit.search.units;

import com.justweighit.units.Unit;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.spell.JaroWinklerDistance;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.search.spell.StringDistance;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class UnitSearcher {
	
	private final Logger logger = Logger.getLogger(UnitSearcher.class.getName());
	private final StandardAnalyzer analyzer;
	private final Directory index;
	
	public UnitSearcher() {
		try {
			analyzer = new StandardAnalyzer();
			index = new RAMDirectory();
			
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			
			IndexWriter w = new IndexWriter(index, config);
			addDoc(w, "teaspoon", Unit.teaspoon);
			addDoc(w, "tsp", Unit.teaspoon);
			addDoc(w, "tablespoon", Unit.tablespoon);
			addDoc(w, "tbsp", Unit.tablespoon);
			addDoc(w, "cups", Unit.cups);
			addDoc(w, "oz", Unit.ounces);
			addDoc(w, "ounces", Unit.ounces);
			addDoc(w, "fluid ounces", Unit.flOz);
			addDoc(w, "fl oz", Unit.flOz);
			w.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Unit> run(String queryStr) {
		
		IndexReader reader = null;
		SpellChecker spellChecker = null;
		try {
			Query q = new QueryParser("unit", analyzer).parse(QueryParser.escape(queryStr));
			
			int hitsPerPage = 10;
			reader = DirectoryReader.open(index);
			
			IndexSearcher searcher = new IndexSearcher(reader);
			TopDocs docs = searcher.search(q, hitsPerPage);
			ScoreDoc[] hits = docs.scoreDocs;
			
			if (hits.length == 0) {
				PlainTextDictionary dictionary = new PlainTextDictionary(getClass().getResourceAsStream("/unitDictionary.txt"));
				spellChecker = new SpellChecker(index, new JaroWinklerDistance());
				spellChecker.indexDictionary(dictionary, new IndexWriterConfig(analyzer), true);
				
				String[] strings = spellChecker.suggestSimilar(queryStr, 5, 0.5f);
				
				logger.info("	suggesting " + Arrays.toString(strings));
				if (strings.length > 0) {
					logger.info("Suggesting " + strings[0]);
					return run(strings[0]);
				} else {
					logger.info("Couldn't suggest anything");
					return new ArrayList<>();
				}
			} else {
				return Arrays.stream(hits).map(hit -> {
					try {
						return Unit.valueOf(searcher.doc(hit.doc).get("id"));
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}).collect(Collectors.toList());
			}
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ignored) {}
			}
			if (spellChecker != null) {
				try {
					spellChecker.close();
				} catch (IOException ignored) {}
			}
		}
	}
	
	private static void addDoc(IndexWriter w, String unitName, Unit unit) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("unit", unitName, Field.Store.YES));
		doc.add(new StringField("id", unit.name(), Field.Store.YES));
		w.addDocument(doc);
	}
	
	public static void debugHits(String query) throws IOException, ParseException {
		List<Unit> hits = new UnitSearcher().run(query);
		
		System.out.println(query + " | Found " + hits.size() + " hits");
		for (Unit hit : hits) {
			System.out.println(hit);
		}
		System.out.println();
	}
	
	public static void main(String[] args) throws IOException, ParseException {
		debugHits("floz");
		debugHits("fl od");
		debugHits("flsod");
	}
}
