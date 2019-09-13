package br.com.caelum.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hsqldb.jdbc.JDBCPool;

public class ConnectionPool {
	
	private final DataSource dataSource;

	public ConnectionPool() {
		System.out.print("iniciando connection pool...");
		JDBCPool pool = new JDBCPool();
		pool.setUrl("jdbc:hsqldb:hsql://localhost/loja-virtual");
		pool.setUser("SA");
		pool.setPassword("");
		dataSource = pool;
		System.out.println(" ok");
	}
	
	public Connection getConnection() throws SQLException {
		System.out.print("adiquirindo conexão...");
		Connection connection = dataSource.getConnection();
		System.out.println(" ok");
		return connection;
	}	

}
