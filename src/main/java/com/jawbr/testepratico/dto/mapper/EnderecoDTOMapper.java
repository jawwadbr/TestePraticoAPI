package com.jawbr.testepratico.dto.mapper;

import com.jawbr.testepratico.dto.EnderecoDTO;
import com.jawbr.testepratico.dto.PessoaDTO;
import com.jawbr.testepratico.entity.Endereco;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class EnderecoDTOMapper implements Function<Endereco, EnderecoDTO> {

    @Override
    public EnderecoDTO apply(Endereco endereco) {
        return new EnderecoDTO(
                endereco.getLogradouro(),
                endereco.getCep(),
                endereco.getNumero(),
                endereco.getCidade(),
                endereco.isEnderecoPrincipal()
        );
    }

    public PessoaDTO applyEnderecoFromPessoa(Endereco endereco) {
        return new PessoaDTO(
                endereco.getPessoa().getNome(),
                endereco.getPessoa().getId(),
                null,
                List.of(
                        new PessoaDTO.EnderecoDTO(
                                endereco.getLogradouro(),
                                endereco.getCep(),
                                endereco.getNumero(),
                                endereco.getCidade(),
                                endereco.isEnderecoPrincipal(),
                                endereco.getId())),
                null
        );
    }
}
