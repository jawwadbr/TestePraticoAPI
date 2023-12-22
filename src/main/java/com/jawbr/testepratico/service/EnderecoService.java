package com.jawbr.testepratico.service;

import com.jawbr.testepratico.dto.PessoaDTO;
import com.jawbr.testepratico.dto.ResponseEntityDTO;
import com.jawbr.testepratico.dto.mapper.EnderecoDTOMapper;
import com.jawbr.testepratico.exception.EnderecoNotFoundException;
import com.jawbr.testepratico.exception.InvalidParameterException;
import com.jawbr.testepratico.repository.EnderecoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final EnderecoDTOMapper enderecoDTOMapper;

    public EnderecoService(EnderecoRepository enderecoRepository, EnderecoDTOMapper enderecoDTOMapper) {
        this.enderecoRepository = enderecoRepository;
        this.enderecoDTOMapper = enderecoDTOMapper;
    }

    // Consultar endereco de pessoa
    public ResponseEntityDTO findEnderecoFromPessoa(String nome, Integer id) {
        if(nome != null) {
            List<PessoaDTO> enderecoFromPessoaNome = Optional.of(enderecoRepository.findEnderecoByPessoaNome(nome))
                    .filter(list -> !list.isEmpty())
                    .map(list -> list.stream().map(enderecoDTOMapper::applyEnderecoFromPessoa).toList())
                    .orElseThrow(() -> new EnderecoNotFoundException(String.format("Endereços da pessoa '%s' não foi encontrado.", nome)));
            return new ResponseEntityDTO(enderecoFromPessoaNome);
        }
        else if(id != null) {
            List<PessoaDTO> enderecoFromPessoaId = Optional.of(enderecoRepository.findEnderecoByPessoaId(id))
                    .filter(list -> !list.isEmpty())
                    .map(list -> list.stream().map(enderecoDTOMapper::applyEnderecoFromPessoa).toList())
                    .orElseThrow(() -> new EnderecoNotFoundException(String.format("Endereços da pessoa com id '%o' não foi encontrado.", id)));
            return new ResponseEntityDTO(enderecoFromPessoaId);
        }

        throw new InvalidParameterException("Por favor insira o parametro 'nome' ou 'id' para a consulta de endereços.");
    }

    // Informar qual endereco é o principal
    public void updateEnderecoPrincipal(int enderecoId, int pessoaId) {

    }
}
