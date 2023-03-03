package com.jawbr.testepratico.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jawbr.testepratico.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{

	public Pessoa findByNome(String pessoaNome);
}
