package com.justweighit.ndb;

import java.util.List;

/**
 * list		information about the items returned
 * q		terms requested and used in the search
 * start	beginning item in the list
 * end		last item in the list
 * offset	beginning offset into the results list for the items in the list requested
 * total	total # of items returned by the search
 * sort		requested sort order (r=relevance or n=name)
 * fg		food group filter
 * sr		Standard Release version of the data being reported
 * item		individual items on the list
 */
public class NDBSearchResponseWrapper {
	
	private NDBSearchResponse list;
	
	public NDBSearchResponse getList() {
		return list;
	}
	
	public void setList(NDBSearchResponse list) {
		this.list = list;
	}
	
	public static class NDBSearchResponse {
		
		private String q;
		private int start;
		private int end;
		private NDBSort sort;
		private int total;
		private String fg;
		private String ds;
		private Integer offset;
		private List<NDBSearchItem> item;
		private String sr;
		private String group;
		
		public String getQ() {
			return q;
		}
		
		public void setQ(String q) {
			this.q = q;
		}
		
		public int getStart() {
			return start;
		}
		
		public void setStart(int start) {
			this.start = start;
		}
		
		public int getEnd() {
			return end;
		}
		
		public void setEnd(int end) {
			this.end = end;
		}
		
		public NDBSort getSort() {
			return sort;
		}
		
		public void setSort(NDBSort sort) {
			this.sort = sort;
		}
		
		public int getTotal() {
			return total;
		}
		
		public void setTotal(int total) {
			this.total = total;
		}
		
		public String getFg() {
			return fg;
		}
		
		public void setFg(String fg) {
			this.fg = fg;
		}
		
		public String getDs() {
			return ds;
		}
		
		public void setDs(String ds) {
			this.ds = ds;
		}
		
		public Integer getOffset() {
			return offset;
		}
		
		public void setOffset(Integer offset) {
			this.offset = offset;
		}
		
		public List<NDBSearchItem> getItem() {
			return item;
		}
		
		public void setItem(List<NDBSearchItem> item) {
			this.item = item;
		}
		
		public String getSr() {
			return sr;
		}
		
		public void setSr(String sr) {
			this.sr = sr;
		}
		
		public String getGroup() {
			return group;
		}
		
		public void setGroup(String group) {
			this.group = group;
		}
	}
}
