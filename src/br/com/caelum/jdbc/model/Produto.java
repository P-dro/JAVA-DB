package br.com.caelum.jdbc.model;

public class Produto {

	private final String descricao;
	private final String nome;
	private int id;

	public Produto(String nome, String descricao) {
		this.nome = nome;
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setId(int id) {
		this.id = id;
	}

}
