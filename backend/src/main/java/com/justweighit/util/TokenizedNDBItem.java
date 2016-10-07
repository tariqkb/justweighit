package com.justweighit.util;

import com.justweighit.ndb.NDBSearchItem;

import java.util.Comparator;

public class TokenizedNDBItem {
	
	private String[] tokens;
	private NDBSearchItem item;
	
	public TokenizedNDBItem(NDBSearchItem item) {
		this.item = item;
		this.tokens = item.getName().split(",");
	}
	
	public static Comparator<TokenizedNDBItem> comparator(String searchText) {
		return (o1, o2) -> {
			int indicies = Math.min(o1.tokens.length, o2.tokens.length);
			
			for (int i = 0; i < indicies; i++) {
				String token1 = o1.tokens[i];
				String token2 = o2.tokens[i];
				boolean has1 = token1.contains(searchText);
				boolean has2 = token2.contains(searchText);
				
				if(has1 && !has2) {
					return -1;
				} else if(!has1 && has2) {
					return 1;
				}
			}
			
			return o1.tokens.length - o2.tokens.length;
		};
	}
	
	public String[] getTokens() {
		return tokens;
	}
	
	public NDBSearchItem getItem() {
		return item;
	}
}
