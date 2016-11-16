package com.justweighit.search.tokens;

import org.apache.lucene.analysis.payloads.PayloadHelper;
import org.apache.lucene.index.FieldInvertState;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.util.BytesRef;

public class PayloadSimilarity extends ClassicSimilarity {
	
	
	@Override
	public float tf(float freq) {
		return 1.0f;
	}
	
	@Override
	public float idf(long docFreq, long docCount) {
		return 1.0f;
	}
	
	@Override
	public float lengthNorm(FieldInvertState state) {
		return 1.0f;
	}
	
	@Override
	public float scorePayload(int doc, int start, int end, BytesRef payload) {
		return PayloadHelper.decodeFloat(payload.bytes, payload.offset);
	}
}
