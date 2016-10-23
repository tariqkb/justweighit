package com.justweighit.database;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultConnectionProvider;

import java.sql.Connection;
import java.sql.SQLException;

public class WritableDatabaseConnector extends DatabaseConnector {
	
	public WritableDatabaseConnector(DatabaseConfig config) {
		super(config);
	}
	
	public DSLContext context() throws SQLException {
		Connection connection = connect();
	
		connection.setReadOnly(false);
		connection.setAutoCommit(false);
		
		return DSL.using(new DefaultConfiguration().set(new DefaultConnectionProvider(connection))
			.set(new Settings().withExecuteLogging(false))
			.set(SQLDialect.POSTGRES));
	}
}
