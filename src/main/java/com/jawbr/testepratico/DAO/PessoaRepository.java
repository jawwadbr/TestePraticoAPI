package com.jawbr.testepratico.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jawbr.testepratico.entity.Pessoa;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{

	Optional<Pessoa> findByNome(String pessoaNome);

    @Modifying
    @Transactional
    @Query("UPDATE Pessoa p SET p.nome = :#{#pessoa.nome}, " +
            "p.dataDeNascimento = :#{#pessoa.dataDeNascimento} " +
            "WHERE p.id = :#{#pessoa.id}")
    void update(@Param("pessoa") Pessoa pessoa);
}
