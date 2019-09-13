package br.com.caelum.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaInsercao {

	public static void main(String[] args) throws SQLException {
		try (Connection connection = new ConnectionPool().getConnection()) {
			connection.setAutoCommit(false);
			String sql = "insert into produto (nome, descricao) values (?, ?)";
			try (PreparedStatement statement = connection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS)) {
				adiciona("TV LCD", "32 polegadas", statement);
				adiciona("Blueray", "Full HDMI", statement);

				connection.commit();
				statement.close();
			} catch (Exception e) {
				connection.rollback();
				System.out.println("Rollback efetuado");
				e.printStackTrace();
			}
		}
	}

	private static void adiciona(String nome, String descricao,
			PreparedStatement statement) throws SQLException {
		if (nome.equals("Blueray")) {
			throw new IllegalArgumentException("Problema ocorrido");
		}
		statement.setString(1, nome);
		statement.setString(2, descricao);
		boolean resultado = statement.execute();
		System.out.println(resultado);
		try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
			while (generatedKeys.next()) {
				long id = generatedKeys.getLong("id");
				System.out.println("id gerado: " + id);
			}
		}
	}
}
