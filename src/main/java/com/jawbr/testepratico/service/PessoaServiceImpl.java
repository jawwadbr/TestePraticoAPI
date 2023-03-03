package com.jawbr.testepratico.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jawbr.testepratico.DAO.PessoaRepository;
import com.jawbr.testepratico.customException.PessoaNotFoundException;
import com.jawbr.testepratico.entity.Pessoa;

@Service
public class PessoaServiceImpl implements PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Override
	public List<Pessoa> findAll() {
		return pessoaRepository.findAll();
	}

	@Override
	public void save(Pessoa pessoa) {
		pessoaRepository.save(pessoa);
	}

	@Override
	public Pessoa findById(int pessoaId) {
		
		Optional<Pessoa> result = pessoaRepository.findById(pessoaId);
		
		if(result.isPresent()) {
			return result.get();
		}
		else {
			throw new PessoaNotFoundException("Pessoa não encontrada - " + pessoaId);
		}
	}

	@Override
	public Pessoa findByNome(String nomePessoa) {
		return pessoaRepository.findByNome(nomePessoa);
	}

	@Override
	public void delete(Pessoa thePessoa) {
		pessoaRepository.delete(thePessoa);
	}

}