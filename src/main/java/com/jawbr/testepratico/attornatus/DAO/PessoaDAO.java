package com.jawbr.testepratico.attornatus.DAO;

import java.util.List;

import com.jawbr.testepratico.attornatus.entity.Pessoa;

public interface PessoaDAO {

	public List<Pessoa> getAllPessoas();

	public void savePessoa(Pessoa pessoa);

	public Pessoa getPessoa(int pessoaId);

	public Pessoa getPessoa(String nomePessoa);

	public void updatePessoa(Pessoa thePessoa);

	public void deletePessoa(Pessoa thePessoa);
	
}
