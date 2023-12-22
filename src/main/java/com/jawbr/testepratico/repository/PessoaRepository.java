package com.jawbr.testepratico.repository;

import com.jawbr.testepratico.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    List<Pessoa> findByNome(String pessoaNome);

}
