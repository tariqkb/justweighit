package com.justweighit.search.tokens;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.miscellaneous.TrimFilter;
import org.apache.lucene.analysis.util.CharTokenizer;

public class PayloadAnalyzer extends Analyzer {
	
	public PayloadAnalyzer() {
		super();
	}
	
	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		Tokenizer source = CharTokenizer.fromSeparatorCharPredicate(c -> c == ',');
		
		TokenStream result = new LowerCaseFilter(source);
		
		result = new TrimFilter(result);
		
		result = new IndexTokenFilter(result);
		
		result = new NDBPayloadTokenFilter(result, new float[]{10f, 7f, 5f, 1f});
		
		
		return new TokenStreamComponents(source, result);
	}
	
}
