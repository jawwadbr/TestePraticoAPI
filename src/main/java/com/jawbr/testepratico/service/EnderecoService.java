package com.jawbr.testepratico.service;

import java.util.List;

import com.jawbr.testepratico.entity.Endereco;

public interface EnderecoService {

	public List<Endereco> findAll();
	
	public Endereco findById(int enderecoId);
	
	public void save(Endereco endereco);
	
}
