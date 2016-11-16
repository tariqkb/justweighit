package com.justweighit.search.tokens;

import org.apache.lucene.util.Attribute;

public interface IndexAttribute extends Attribute {
	
	void setIndex(int index);
	
	int index();
}
