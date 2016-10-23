package com.justweighit.database;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultConnectionProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {
	
	private DatabaseConfig config;
	
	public DatabaseConnector(DatabaseConfig config) {
		this.config = config;
	}
	
	public Connection connect() throws SQLException {
		Properties props = new java.util.Properties();
		props.put("user", config.getUsername());
		props.put("password", config.getPassword());
		
		Connection conn = DriverManager.getConnection(config.getUrl(), props);
		conn.setReadOnly(true);
		conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		//conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
		conn.setAutoCommit(false);
		return conn;
	}
	
	public DSLContext context() throws SQLException {
		Connection connection = connect();
		
		connection.setReadOnly(true);
		connection.setAutoCommit(false);
		
		return DSL.using(new DefaultConfiguration().set(new DefaultConnectionProvider(connection))
			.set(new Settings().withExecuteLogging(false))
			.set(SQLDialect.POSTGRES));
	}
	
}