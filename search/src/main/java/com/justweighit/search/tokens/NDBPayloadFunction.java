package com.justweighit.search.tokens;

import org.apache.lucene.queries.payloads.AveragePayloadFunction;

public class NDBPayloadFunction extends AveragePayloadFunction {
	
	@Override
	public float docScore(int docId, String field, int numPayloadsSeen, float payloadScore) {
		return numPayloadsSeen > 0 ? payloadScore : 1;
	}
}
