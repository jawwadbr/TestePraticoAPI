package com.jawbr.testepratico.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jawbr.testepratico.DAO.EnderecoRepository;
import com.jawbr.testepratico.customException.EnderecoNotFoundException;
import com.jawbr.testepratico.entity.Endereco;

@Service
public class EnderecoServiceImpl implements EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Override
	public List<Endereco> findAll() {
		return enderecoRepository.findAll();
	}

	@Override
	public void save(Endereco endereco) {
		enderecoRepository.save(endereco);
	}

	@Override
	public Endereco findById(int enderecoId) {
		
		Optional<Endereco> result = enderecoRepository.findById(enderecoId);
		if(result.isPresent()) {
			return result.get();
		}
		else
			throw new EnderecoNotFoundException("Endereço não encontrado - " + enderecoId);
		
	}

}
