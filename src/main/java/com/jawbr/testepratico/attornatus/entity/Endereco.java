package com.jawbr.testepratico.attornatus.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.jawbr.testepratico.attornatus.jsonViews.PessoaComEndereco;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "endereco")
public class Endereco {

	@JsonView(PessoaComEndereco.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@JsonView(PessoaComEndereco.class)
	@Column(name = "logradouro")
	private String logradouro;
	
	@JsonView(PessoaComEndereco.class)
	@Column(name = "cep")
	private long cep;
	
	@JsonView(PessoaComEndereco.class)
	@Column(name = "numero")
	private int numero;
	
	@JsonView(PessoaComEndereco.class)
	@Column(name = "cidade")
	private String cidade;
	
	@JsonView(PessoaComEndereco.class)
	@Column(name = "endereco_principal")
	private boolean enderecoPrincipal;
	
	public Endereco() {}

	public Endereco(String logradouro, long cep, int numero, String cidade) {
		super();
		this.logradouro = logradouro;
		this.cep = cep;
		this.numero = numero;
		this.cidade = cidade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public long getCep() {
		return cep;
	}

	public void setCep(int cep) {
		this.cep = cep;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public boolean isEnderecoPrincipal() {
		return enderecoPrincipal;
	}

	public void setEnderecoPrincipal(boolean enderecoPrincipal) {
		this.enderecoPrincipal = enderecoPrincipal;
	}

	@Override
	public String toString() {
		return "Endereco [id=" + id + ", logradouro=" + logradouro + ", cep=" + cep + ", numero=" + numero + ", cidade="
				+ cidade + ", enderecoPrincipal=" + enderecoPrincipal + "]";
	}
	
}
