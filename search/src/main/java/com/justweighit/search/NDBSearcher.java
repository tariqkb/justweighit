package com.justweighit.search;

import com.justweighit.database.DatabaseConfig;
import com.justweighit.database.DatabaseConnector;
import com.justweighit.database.jooq.tables.records.FoodRecord;
import com.justweighit.search.tokens.NDBPayloadFunction;
import com.justweighit.search.tokens.PayloadAnalyzer;
import com.justweighit.search.tokens.PayloadSimilarity;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.payloads.AveragePayloadFunction;
import org.apache.lucene.queries.payloads.PayloadScoreQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.spans.SpanOrQuery;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.justweighit.database.jooq.Tables.FOOD;

public class NDBSearcher {
	
	private final Logger logger;
	private final Analyzer analyzer;
	private final Directory index;
	
	private final static String FIELD_ID = "id";
	private final static String FIELD_TERMS = "terms";
	private PayloadSimilarity payloadSimilarity;
	
	public NDBSearcher(DSLContext context) {
		this.logger = Logger.getLogger(NDBSearcher.class.getName());
		try {
			
			analyzer = new PayloadAnalyzer();
//			index = new RAMDirectory();
			index = new SimpleFSDirectory(Paths.get("src/java/resources/jwiIndex"));
			payloadSimilarity = new PayloadSimilarity();
			
			IndexWriterConfig config = new IndexWriterConfig(analyzer)
				.setOpenMode(OpenMode.CREATE).setSimilarity(payloadSimilarity);
			
			IndexWriter w = new IndexWriter(index, config);
			Result<FoodRecord> foods = context.select().from(FOOD).limit(10).fetchInto(FOOD);
			
			logger.info("Found " + foods.size() + " foods");
			logger.info(foods.format());
			for (FoodRecord food : foods) {
				Document doc = new Document();
				doc.add(new StringField(FIELD_ID, food.getNdbno(), Store.YES));
				doc.add(new TextField(FIELD_TERMS, food.getDescription(), Store.YES));
				w.addDocument(doc);
			}
			
			w.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<NDBSearcherResult> findNdbno(String queryStr) {
		try {
			
			logger.info("Query str: " + queryStr);
			
			SpanTermQuery[] orTerms = Arrays.stream(queryStr.split(" ")).map(queryTerm ->
				new SpanTermQuery(new Term(FIELD_TERMS, queryTerm))
			).toArray(SpanTermQuery[]::new);
			
			PayloadScoreQuery query = new PayloadScoreQuery(new SpanOrQuery(orTerms), new NDBPayloadFunction(), true);
//			PayloadScoreQuery query = new PayloadScoreQuery(new SpanTermQuery(new Term(FIELD_TERMS, queryStr)), new AveragePayloadFunction(), false);
			
			logger.info("Query: " + query);
			IndexReader reader = DirectoryReader.open(index);
			IndexSearcher searcher = new IndexSearcher(reader);
			searcher.setSimilarity(payloadSimilarity);
			TopDocs docs = searcher.search(query, 10);
			
			return Arrays.stream(docs.scoreDocs).map(hit -> {
				try {
					return new NDBSearcherResult(searcher.doc(hit.doc).get(FIELD_ID),
						searcher.doc(hit.doc).get(FIELD_TERMS));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}).collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) throws SQLException {
		DSLContext context = DSL.using(
			new DatabaseConnector(
				new DatabaseConfig("jdbc:postgresql://localhost:5432/justweighit", "jwi", "")).connect(),
			SQLDialect.POSTGRES);
		NDBSearcher searcher = new NDBSearcher(context);
		List<NDBSearcherResult> ndbs = searcher.findNdbno(args[0]);
		
		System.out.println("--- Results ---");
		ndbs.forEach(System.out::println);
	}
	
}
