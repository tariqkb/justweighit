package com.justweighit.search.tokens;

import org.apache.lucene.util.AttributeImpl;
import org.apache.lucene.util.AttributeReflector;

public class IndexAttributeImpl extends AttributeImpl implements IndexAttribute {
	
	private int index = -1;
	
	@Override
	public void setIndex(int index) {
		this.index = index;
	}
	
	@Override
	public int index() {
		return index;
	}
	
	@Override
	public void clear() {
		index = -1;
	}
	
	@Override
	public void reflectWith(AttributeReflector reflector) {
		reflector.reflect(IndexAttribute.class, "index", index);
	}
	
	@Override
	public void copyTo(AttributeImpl target) {
		((IndexAttribute) target).setIndex(index);
	}
}
