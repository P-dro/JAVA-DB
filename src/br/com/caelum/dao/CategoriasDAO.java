package br.com.caelum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.jdbc.model.Categoria;
import br.com.caelum.jdbc.model.Produto;

public class CategoriasDAO {

	private final Connection con;

	public CategoriasDAO(Connection con) {
		this.con = con;
	}

	public List<Produto> lista() throws SQLException {
		List<Produto> produtos = new ArrayList<>();
		String sql = "select * from Produto";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.execute();
			transformaResultadoEmProdutos(stmt, produtos);
		}
		return produtos;
	}

	private void transformaResultadoEmProdutos(PreparedStatement stmt, List<Produto> produtos) throws SQLException {
		try (ResultSet rs = stmt.getResultSet()) {
			while (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String descricao = rs.getString("descricao");
				Produto produto = new Produto(nome, descricao);
				produto.setId(id);
				produtos.add(produto);
			}
		}
	}

	public List<Categoria> listaComProdutos() throws SQLException {
		List<Categoria> categorias = new ArrayList<>();
        Categoria ultima = null;
		String sql = "select c.id as c_id, c.nome as c_nome, p.id as p_id, p.nome as p_nome, p.descricao as p_descricao from Categoria as c join Produto as p on p.categoria_id = c.id";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.execute();
			try (ResultSet rs = stmt.getResultSet()) {

				while(rs.next()) {
                    int id = rs.getInt("c_id");
                    String nome = rs.getString("c_nome");
                    if(ultima==null || !ultima.getNome().equals(nome)) {
                        Categoria categoria = new Categoria(id, nome);
                        categorias.add(categoria);
                        ultima = categoria;
                    }
                    int idDoProduto = rs.getInt("p_id");
                    String nomeDoProduto =rs.getString("p_nome");
                    String descricaoDoProduto = rs.getString("p_descricao");
                    Produto p = new Produto(nomeDoProduto, descricaoDoProduto);
                    p.setId(idDoProduto);
                    ultima.adiciona(p);
                }
			}
		}
		return categorias;
	}
}