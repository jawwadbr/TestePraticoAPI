package com.jawbr.testepratico.attornatus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jawbr.testepratico.attornatus.DAO.PessoaDAO;
import com.jawbr.testepratico.attornatus.entity.Pessoa;

@Service
public class PessoaServiceImpl implements PessoaService {
	
	@Autowired
	private PessoaDAO pessoaDAO;
	
	@Override
	@Transactional
	public List<Pessoa> getAllPessoas() {
		return pessoaDAO.getAllPessoas();
	}

	@Override
	@Transactional
	public void savePessoa(Pessoa pessoa) {
		pessoaDAO.savePessoa(pessoa);
	}

	@Override
	@Transactional
	public Pessoa getPessoa(int pessoaId) {
		return pessoaDAO.getPessoa(pessoaId);
	}

	@Override
	@Transactional
	public Pessoa getPessoa(String nomePessoa) {
		return pessoaDAO.getPessoa(nomePessoa);
	}

	@Override
	@Transactional
	public void updatePessoa(Pessoa thePessoa) {
		pessoaDAO.updatePessoa(thePessoa);
	}

	@Override
	@Transactional
	public void deletePessoa(Pessoa thePessoa) {
		pessoaDAO.deletePessoa(thePessoa);
	}

}