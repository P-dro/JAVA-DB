package br.com.caelum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.jdbc.model.Produto;

public class ProdutoDao {

	private final Connection connection;

	public ProdutoDao(Connection connection) {
		this.connection = connection;
	}

	public void salva(Produto produto) throws SQLException {
		try (PreparedStatement stmt = connection.prepareStatement(
				"insert into produto(nome, descricao) values (?, ?)",
				Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, produto.getNome());
			stmt.setString(2, produto.getDescricao());
			stmt.execute();
			try (ResultSet keys = stmt.getGeneratedKeys()) {
				keys.next();
				int id = keys.getInt("id");
				produto.setId(id);
			}
		}
	}

	public List<Produto> lista() throws SQLException {
		String sql = "select * from produto";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.execute();
			ResultSet resultSet = stmt.getResultSet();
			ArrayList<Produto> produtos = new ArrayList<>();
			while(resultSet.next()) {
				String nome = resultSet.getString("nome");
				String descricao = resultSet.getString("descricao");
				int id = resultSet.getInt("id");
				Produto p = new Produto(nome, descricao);
				p.setId(id);
				produtos.add(p);
			}
			return produtos;
		}
	}

}
