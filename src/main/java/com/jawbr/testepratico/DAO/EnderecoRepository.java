package com.jawbr.testepratico.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jawbr.testepratico.entity.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

}
