package com.justweighit.search.tokens;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.miscellaneous.TrimFilter;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.CharTokenizer;

public class NDBAnalyzer extends Analyzer {
	
	public NDBAnalyzer() {
		super();
	}
	
	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		Tokenizer source = CharTokenizer.fromSeparatorCharPredicate(c -> c == ',' || Character.isWhitespace(c));
		
		TokenStream result = new StandardFilter(source);
		
		result = new LowerCaseFilter(result);
		
		result = new EnglishPossessiveFilter(result);
		result = new org.apache.lucene.analysis.LowerCaseFilter(result);
		result = new PorterStemFilter(result);
		
		result = new TrimFilter(result);
		
		result = new IndexTokenFilter(result);
		
		result = new NDBPayloadTokenFilter(result, new float[]{10f, 7f, 5f, 1f});
		
		
		return new TokenStreamComponents(source, result);
	}
	
}
