package com.justweighit.search;

import com.justweighit.database.DatabaseConfig;
import com.justweighit.database.DatabaseConnector;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class NDBSearcherTest {
	
	private final NDBSearcher searcher;
	
	public NDBSearcherTest() throws SQLException {
		DSLContext context = DSL.using(
			new DatabaseConnector(
				new DatabaseConfig("jdbc:postgresql://localhost:5432/justweighit", "jwi", "")).connect(),
			SQLDialect.POSTGRES);
		this.searcher = new NDBSearcher();
	}
	
	@Test
	public void test() {
		List<NDBSearcherResult> ndbs = searcher.findNdbno("butter");
		
		System.out.println("--- Results ---");
		ndbs.forEach(System.out::println);
	}
}
