package com.justweighit.search;

import com.justweighit.search.tokens.NDBPayloadFunction;
import com.justweighit.search.tokens.PayloadSimilarity;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.payloads.PayloadScoreQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.spans.SpanOrQuery;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class NDBSearcher {
	
	private final Logger logger;
	
	private final Directory index;
	private final Analyzer analyzer;
	private PayloadSimilarity payloadSimilarity;
	
	public final static String FIELD_ID = "id";
	public final static String FIELD_TERMS = "terms";
	public final static String FIELD_NAME = "name";
	public final static String FIELD_Is_RELATED_FOOD = "is_related_food";
	
	public NDBSearcher(Directory index, Analyzer analyzer, PayloadSimilarity payloadSimilarity) {
		this.logger = Logger.getLogger(NDBSearcher.class.getName());
		this.index = index;
		this.analyzer = analyzer;
		this.payloadSimilarity = payloadSimilarity;
	}
	
	public List<NDBSearcherResult> findNdbno(String queryStr) {
		IndexReader reader = null;
		try {
			
			logger.info("Query str: " + queryStr);
			
			List<SpanTermQuery> orTerms = new ArrayList<>();
			TokenStream s = analyzer.tokenStream(FIELD_TERMS, queryStr);
			try {
				s.reset();
				while (s.incrementToken()) {
					String normTerm = s.getAttribute(CharTermAttribute.class).toString();
					orTerms.add(new SpanTermQuery(new Term(FIELD_TERMS, analyzer.normalize(FIELD_TERMS, normTerm))));
				}
				s.end();
			} catch (Exception e) {
				
			} finally {
				try {
					s.close();
				} catch (IOException e1) {
				}
			}
			
			PayloadScoreQuery query = new PayloadScoreQuery(new SpanOrQuery(orTerms.toArray(new SpanTermQuery[]{})), new NDBPayloadFunction(), true);
			
			logger.info("Query: " + query);
			reader = DirectoryReader.open(index);
			IndexSearcher searcher = new IndexSearcher(reader);
			searcher.setSimilarity(payloadSimilarity);
			TopDocs docs = searcher.search(query, 15, Sort.RELEVANCE);
			
			return Arrays.stream(docs.scoreDocs).map(hit -> {
				try {
					return new NDBSearcherResult(searcher.doc(hit.doc).get(FIELD_ID),
						searcher.doc(hit.doc).get(FIELD_NAME));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}).collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.severe("Could not close index reader.");
				}
			}
		}
	}
	
}
