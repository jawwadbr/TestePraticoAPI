package com.jawbr.testepratico.service;

import java.util.List;
import java.util.Optional;

import com.jawbr.testepratico.entity.Pessoa;

public interface PessoaService {

	List<Pessoa> findAll();
	
	Pessoa findById(int pessoaId);
	
	Pessoa findByNome(String pessoaNome);
	
	void save(Pessoa pessoa);

	void update(Optional<Pessoa> pessoa);

	void delete(int pessoaId);
	
}
