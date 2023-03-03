package com.jawbr.testepratico.service;

import java.util.List;

import com.jawbr.testepratico.entity.Pessoa;

public interface PessoaService {

	public List<Pessoa> findAll();
	
	public Pessoa findById(int pessoaId);
	
	public Pessoa findByNome(String pessoaNome);
	
	public void save(Pessoa pessoa);

	public void delete(Pessoa thePessoa);
	
}
