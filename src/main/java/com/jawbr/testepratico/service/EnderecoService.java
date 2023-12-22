package com.jawbr.testepratico.service;

import com.jawbr.testepratico.dto.PessoaDTO;
import com.jawbr.testepratico.dto.ResponseEntityDTO;
import com.jawbr.testepratico.dto.mapper.EnderecoDTOMapper;
import com.jawbr.testepratico.entity.Endereco;
import com.jawbr.testepratico.entity.Pessoa;
import com.jawbr.testepratico.exception.EnderecoBadRequestException;
import com.jawbr.testepratico.exception.EnderecoNotFoundException;
import com.jawbr.testepratico.exception.InvalidParameterException;
import com.jawbr.testepratico.exception.PessoaNotFoundException;
import com.jawbr.testepratico.repository.EnderecoRepository;
import com.jawbr.testepratico.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final PessoaRepository pessoaRepository;
    private final EnderecoDTOMapper enderecoDTOMapper;

    public EnderecoService(EnderecoRepository enderecoRepository,
                           PessoaRepository pessoaRepository, EnderecoDTOMapper enderecoDTOMapper)
    {
        this.enderecoRepository = enderecoRepository;
        this.pessoaRepository = pessoaRepository;
        this.enderecoDTOMapper = enderecoDTOMapper;
    }

    // Consultar endereco de pessoa
    public ResponseEntityDTO findEnderecoFromPessoa(String nome, Integer id) {
        if(nome != null) {
            List<PessoaDTO> enderecoFromPessoaNome = Optional.of(enderecoRepository
                            .findEnderecoByPessoaNome(nome))
                    .filter(list -> !list.isEmpty())
                    .map(list -> list.stream().map(enderecoDTOMapper::applyEnderecoFromPessoa).toList())
                    .orElseThrow(() -> new EnderecoNotFoundException(
                            String.format("Endereços da pessoa '%s' não foi encontrado.", nome)));
            return new ResponseEntityDTO(enderecoFromPessoaNome);
        }
        else if(id != null) {
            List<PessoaDTO> enderecoFromPessoaId = Optional.of(
                            enderecoRepository.findEnderecoByPessoaId(id))
                    .filter(list -> !list.isEmpty())
                    .map(list -> list.stream().map(enderecoDTOMapper::applyEnderecoFromPessoa).toList())
                    .orElseThrow(() -> new EnderecoNotFoundException(
                            String.format("Endereços da pessoa com id '%d' não foi encontrado.", id)));
            return new ResponseEntityDTO(enderecoFromPessoaId);
        }

        throw new InvalidParameterException(
                "Por favor insira o parametro 'nome' ou 'id' para a consulta de endereços.");
    }

    // Informar qual endereco é o principal
    public void updateEnderecoPrincipal(int enderecoId, int pessoaId) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new PessoaNotFoundException(
                        String.format("Pessoa com id '%d' não foi encontrada.", pessoaId)));

        Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new EnderecoNotFoundException(
                        String.format("Endereços de id '%d' não foi encontrado, não foi possível atualizar endereço principal.", enderecoId)));

        pessoa.getEnderecos().forEach(enderecos -> enderecos.setEnderecoPrincipal(false));

        endereco.setEnderecoPrincipal(true);

        pessoaRepository.save(pessoa);
    }

    public ResponseEntityDTO findEnderecosPrincipalFromPessoa(int pessoaId) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new PessoaNotFoundException(
                        String.format("Pessoa com id '%d' não foi encontrada.", pessoaId)));

        Endereco thePrincipalEndereco = null;
        for(Endereco endereco : pessoa.getEnderecos()) {
            if(endereco.isEnderecoPrincipal()) {
                thePrincipalEndereco = endereco;
            }
        }
        if(thePrincipalEndereco == null) {
            throw new EnderecoBadRequestException("Nenhum endereço principal encontrado.");
        }
        return new ResponseEntityDTO(enderecoDTOMapper.applyEnderecoFromPessoa(thePrincipalEndereco));
    }
}
