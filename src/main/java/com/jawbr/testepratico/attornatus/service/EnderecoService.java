package com.jawbr.testepratico.attornatus.service;

import java.util.List;

import com.jawbr.testepratico.attornatus.entity.Endereco;

public interface EnderecoService {

	public List<Endereco> getAllEnderecos();
	
	public Endereco getEndereco(int enderecoId);
	
	public void saveEndereco(Endereco endereco);

	public void updateEndereco(Endereco theEndereco);
}
