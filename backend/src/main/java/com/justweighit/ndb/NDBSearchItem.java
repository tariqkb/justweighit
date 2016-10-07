package com.justweighit.ndb;

/**
 * ndbno	the food’s NDB Number
 * name		the food’s name
 * group	food group to which the food belongs
 * ds		Data source: BL=Branded Food Products or SR=Standard Release
 */

public class NDBSearchItem {
	
	private String ndbno;
	private String name;
	private String group;
	private String ds;
	private Integer offset;
	
	public String getNdbno() {
		return ndbno;
	}
	
	public void setNdbno(String ndbno) {
		this.ndbno = ndbno;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getGroup() {
		return group;
	}
	
	public void setGroup(String group) {
		this.group = group;
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
}
