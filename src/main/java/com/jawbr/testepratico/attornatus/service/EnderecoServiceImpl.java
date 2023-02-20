package com.jawbr.testepratico.attornatus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jawbr.testepratico.attornatus.DAO.EnderecoDAO;
import com.jawbr.testepratico.attornatus.entity.Endereco;

@Service
public class EnderecoServiceImpl implements EnderecoService {

	@Autowired
	private EnderecoDAO enderecoDAO;
	
	@Override
	@Transactional
	public List<Endereco> getAllEnderecos() {
		return enderecoDAO.getAllEnderecos();
	}

	@Override
	@Transactional
	public void saveEndereco(Endereco endereco) {
		enderecoDAO.saveEndereco(endereco);
	}

	@Override
	@Transactional
	public Endereco getEndereco(int enderecoId) {
		return enderecoDAO.getEndereco(enderecoId);
	}

	@Override
	@Transactional
	public void updateEndereco(Endereco theEndereco) {
		enderecoDAO.updateEndereco(theEndereco);
	}

}
