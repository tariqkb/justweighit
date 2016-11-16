package com.justweighit.search.tokens;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.util.logging.Logger;

public final class IndexTokenFilter extends TokenFilter {
	
	private final CharTermAttribute charAtt = addAttribute(CharTermAttribute.class);
	private final IndexAttribute indexAtt = addAttribute(IndexAttribute.class);
	
	private int index = 0;
	
	protected IndexTokenFilter(TokenStream input) {
		super(input);
	}
	
	@Override
	public boolean incrementToken() throws IOException {
		if(input.incrementToken()) {
			indexAtt.setIndex(index++);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void reset() throws IOException {
		super.reset();
		
		index = 0;
	}
}
