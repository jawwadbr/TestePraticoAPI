package com.jawbr.testepratico.service;

import com.jawbr.testepratico.entity.Endereco;

import java.util.List;
import java.util.Optional;

public interface EnderecoService {

    public List<Endereco> findAll();

    public Endereco findById(int enderecoId);

    public void saveEnderecoPessoa(Endereco endereco, int pessoaId);

    void update(Optional<Endereco> endereco);

    void updateEnderecoPrincipal(int enderecoId, int pessoaId);

}
