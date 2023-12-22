package com.jawbr.testepratico.repository;

import com.jawbr.testepratico.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    @Query("SELECT e FROM Endereco e WHERE e.pessoa.nome = :nome")
    List<Endereco> findEnderecoByPessoaNome(@Param("nome")String nome);

    @Query("SELECT e FROM Endereco e WHERE e.pessoa.id = :id")
    List<Endereco> findEnderecoByPessoaId(@Param("id")Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE Endereco e SET e.logradouro = :#{#endereco.logradouro}, " +
            "e.cep = :#{#endereco.cep}, " +
            "e.numero = :#{#endereco.numero}, " +
            "e.cidade = :#{#endereco.cidade}, " +
            "e.enderecoPrincipal = :#{#endereco.enderecoPrincipal} " +
            "WHERE e.id = :#{#endereco.id}")
    void update(@Param("endereco") Endereco endereco);
}
