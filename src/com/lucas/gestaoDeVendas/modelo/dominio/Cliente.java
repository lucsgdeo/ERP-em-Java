package com.lucas.gestaoDeVendas.modelo.dominio;

public class Cliente {

	private Long id;
	private String nome;
	private String telefone;
	private String edndereco;
	
	public Cliente() {
	}
	
	public Cliente(Long id, String nome, String telefone, String edndereco) {
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.edndereco = edndereco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEdndereco() {
		return edndereco;
	}

	public void setEdndereco(String edndereco) {
		this.edndereco = edndereco;
	}
	
}
