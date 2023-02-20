package com.jawbr.testepratico.attornatus.DAO;

import java.util.List;

import com.jawbr.testepratico.attornatus.entity.Endereco;

public interface EnderecoDAO {
	
	public List<Endereco> getAllEnderecos();
	
	public void saveEndereco(Endereco endereco);

	public Endereco getEndereco(int enderecoId);

	public void updateEndereco(Endereco theEndereco);

}
