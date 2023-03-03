package com.jawbr.testepratico.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.jawbr.testepratico.jsonViews.PessoaComEndereco;
import com.jawbr.testepratico.jsonViews.PessoaSemEndereco;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pessoa")
public class Pessoa {
	
	
	@JsonView(PessoaSemEndereco.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@JsonView(PessoaSemEndereco.class)
	@Column(name = "nome")
	private String nome;
	
	@JsonView(PessoaSemEndereco.class)
	@Column(name = "data_nascimento")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataDeNascimento;
	
	@JsonView(PessoaComEndereco.class)
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "pessoa_id")
	private List<Endereco> enderecos;
	
	public Pessoa() {}

	public Pessoa(String nome, LocalDate dataDeNascimento) {
		this.nome = nome;
		this.dataDeNascimento = dataDeNascimento;
	}

	public Pessoa(String nome, LocalDate dataDeNascimento, List<Endereco> enderecos) {
		this.nome = nome;
		this.dataDeNascimento = dataDeNascimento;
		this.enderecos = enderecos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(LocalDate dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}
	
	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", dataDeNascimento=" + dataDeNascimento + "]";
	}
	
	// Metodo de conveniencia para relação bi-direcional
	public void addEndereco(Endereco tempEndereco) {
		if(enderecos == null) {
			enderecos = new ArrayList<>();
		}
		
		enderecos.add(tempEndereco);
	}
	
}

