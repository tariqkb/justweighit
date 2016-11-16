package com.justweighit.search.tokens;

import com.justweighit.search.tokens.IndexAttribute;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.payloads.PayloadHelper;
import org.apache.lucene.analysis.tokenattributes.PayloadAttribute;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;

public final class NDBPayloadTokenFilter extends TokenFilter {
	
	private final PayloadAttribute payAtt = addAttribute(PayloadAttribute.class);
	private final IndexAttribute indexAtt = addAttribute(IndexAttribute.class);
	private final float[] weights;
	
	protected NDBPayloadTokenFilter(TokenStream input, float[] weights) {
		super(input);
		this.weights = weights;
	}
	
	@Override
	public boolean incrementToken() throws IOException {
		if (input.incrementToken()) {
			int index = indexAtt.index();
			
			float weight = index < this.weights.length ? this.weights[index] : this.weights[this.weights.length - 1];
			
			byte[] data = PayloadHelper.encodeFloat(weight);
			BytesRef payload = new BytesRef(data);
			payAtt.setPayload(payload);
			return true;
		} else {
			return false;
		}
	}
}
