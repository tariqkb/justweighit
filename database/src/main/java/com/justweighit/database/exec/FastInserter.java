package com.justweighit.database.exec;

import org.jooq.DSLContext;
import org.jooq.InsertQuery;
import org.jooq.Record;
import org.jooq.Table;

public class FastInserter<R extends Record> {
	
	private final DSLContext context;
	private final Table<R> table;
	private final int rowLimit;
	
	private InsertQuery<R> query;
	private int rows;
	private int totalRows;
	
	public FastInserter(DSLContext context, Table<R> table, int rowLimit) {
		this.context = context;
		this.table = table;
		this.rowLimit = rowLimit;
		
		query = context.insertQuery(table);
		rows = 0;
	}
	
	public void queue(R record) {
		
		query.newRecord();
		query.addRecord(record);
		rows++;
		
		if (rows > rowLimit) {
			insert();
		}
	}
	
	public void insert() {
		if (rows > 0) {
			query.execute();
		}
		query = context.insertQuery(table);
		totalRows += rows;
		rows = 0;
	}
	
	public int rows() {
		return totalRows;
	}
	
}
