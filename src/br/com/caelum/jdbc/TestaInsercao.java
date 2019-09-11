package br.com.caelum.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaInsercao {
	public static void main(String[] args) throws SQLException {
		
		String nome = "Notebook'i5";
		String descricao = "Notebook %& é um, i5";
		
		Connection connection = Database.getConnection();
		
		String sql = "insert into Produto (nome, descricao) values (?, ?)";
		
		PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, nome);
		statement.setString(2, descricao);
		
		boolean resultado = statement.execute();
		System.out.println("O resultado do insert é: " + resultado);
		
		ResultSet resultSet = statement.getGeneratedKeys();
		while(resultSet.next()) {
			String id = resultSet.getString("id");
			System.out.println(id);
		}
			
		statement.close();
		connection.close();
	}
}
