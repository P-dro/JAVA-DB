package br.com.caelum.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.caelum.dao.ProdutoDao;
import br.com.caelum.jdbc.model.Produto;

public class TestaDAO {

	public static void main(String[] args) throws SQLException {
		Produto produto = new Produto("Mesa Azul", "Mesa com 4 pés");
		try (Connection con = new ConnectionPool().getConnection()) {
			ProdutoDao produtoDao = new ProdutoDao(con);
			System.out.println("inserindo");
			produtoDao.salva(produto);
			
			List<Produto> produtos = produtoDao.lista();
			for (Produto p : produtos) {
				System.out.println(p);
			}
		}
		
		
	}
}
