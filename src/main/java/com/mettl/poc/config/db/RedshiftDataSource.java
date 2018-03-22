package com.mettl.poc.config.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.zaxxer.hikari.HikariDataSource;

public class RedshiftDataSource {

	static final String dbURL = "jdbc:redshift://mettl-poc.cjry32llfjfk.ap-south-1.redshift.amazonaws.com:5439/analytics";
	static final String MasterUsername = "mettl";
	static final String MasterUserPassword = "Mettl1234";

	private static RedshiftDataSource redshiftDataSource;
	private HikariDataSource dataSource;

	private RedshiftDataSource() {
		dataSource = new HikariDataSource();
		dataSource.setDriverClassName("com.amazon.redshift.jdbc.Driver");
		dataSource.setJdbcUrl(dbURL);
		dataSource.setUsername(MasterUsername);
		dataSource.setPassword(MasterUserPassword);
		dataSource.setMaximumPoolSize(6);
		dataSource.setConnectionTimeout(300000l);
	}

	public static RedshiftDataSource getInstance() {
		if (redshiftDataSource == null)
			redshiftDataSource = new RedshiftDataSource();
		return redshiftDataSource;
	}

	public Connection getConnection() {
		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public static Connection createNewConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.amazon.redshift.jdbc.Driver");
	    Connection con = DriverManager.getConnection(dbURL, MasterUsername, MasterUserPassword);
		return con;
	}

	
	public static void cleanUp(Statement stmt) {
		try {
			if (stmt != null)
				stmt.close();
		} catch (Exception ex) {
		}
		
	}

}