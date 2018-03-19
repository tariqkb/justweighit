package com.justweighit.util;

import com.justweighit.database.jooq.tables.records.FoodRecord;
import com.justweighit.database.jooq.tables.records.RelatedFoodRecord;
import com.justweighit.search.tokens.NDBAnalyzer;
import com.justweighit.search.tokens.PayloadSimilarity;
import io.dropwizard.lifecycle.Managed;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.jooq.DSLContext;
import org.jooq.Result;

import java.nio.file.Paths;

import static com.justweighit.database.jooq.Tables.FOOD;
import static com.justweighit.database.jooq.Tables.RELATED_FOOD;
import static com.justweighit.search.NDBSearcher.*;

public class NDBIndex implements Managed {
	
	private Directory index;
	private Analyzer analyzer;
	private PayloadSimilarity payloadSimilarity;
	
	private final DSLContext context;
	
	public NDBIndex(DSLContext context) {
		this.context = context;
	}
	
	@Override
	public void start() throws Exception {
		analyzer = new NDBAnalyzer();
		payloadSimilarity = new PayloadSimilarity();
		
		index = new SimpleFSDirectory(Paths.get("./jwiIndex"));
		IndexWriterConfig config = new IndexWriterConfig(analyzer)
			.setOpenMode(OpenMode.CREATE).setSimilarity(payloadSimilarity);
		
		IndexWriter w = new IndexWriter(index, config);
		Result<FoodRecord> foods = context.select().from(FOOD).fetchInto(FOOD);
		
		for (FoodRecord food : foods) {
			Document doc = new Document();
			doc.add(new StringField(FIELD_ID, food.getNdbno(), Store.YES));
			doc.add(new TextField(FIELD_NAME, food.getDescription(), Store.YES));
			doc.add(new TextField(FIELD_TERMS, food.getDescription(), Store.YES));
			doc.add(new StringField(FIELD_Is_RELATED_FOOD, "false", Store.YES));
			w.addDocument(doc);
		}
		
		Result<RelatedFoodRecord> relatedFood = context.fetch(RELATED_FOOD);
		for (RelatedFoodRecord food : relatedFood) {
			Document doc = new Document();
			doc.add(new StringField(FIELD_ID, food.getNdbno(), Store.YES));
			doc.add(new TextField(FIELD_NAME, food.getName(), Store.YES));
			doc.add(new TextField(FIELD_TERMS, food.getDescription(), Store.YES));
			doc.add(new StringField(FIELD_Is_RELATED_FOOD, "true", Store.YES));
			w.addDocument(doc);
		}
		
		w.close();
	}
	
	@Override
	public void stop() throws Exception {
		
	}
	
	public Directory getIndex() {
		return index;
	}
	
	public Analyzer getAnalyzer() {
		return analyzer;
	}
	
	public PayloadSimilarity getPayloadSimilarity() {
		return payloadSimilarity;
	}
}
