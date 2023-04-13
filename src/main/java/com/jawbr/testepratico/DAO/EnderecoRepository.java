package com.jawbr.testepratico.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jawbr.testepratico.entity.Endereco;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

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
