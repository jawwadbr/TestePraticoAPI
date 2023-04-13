package com.jawbr.testepratico.service;

import com.jawbr.testepratico.DAO.EnderecoRepository;
import com.jawbr.testepratico.DAO.PessoaRepository;
import com.jawbr.testepratico.customException.EnderecoBadRequestException;
import com.jawbr.testepratico.customException.EnderecoNotFoundException;
import com.jawbr.testepratico.entity.Endereco;
import com.jawbr.testepratico.entity.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final PessoaService pessoaService;

    @Autowired
    public EnderecoServiceImpl(EnderecoRepository enderecoRepository, PessoaService pessoaService) {
        this.enderecoRepository = enderecoRepository;
        this.pessoaService = pessoaService;
    }

    @Override
    public List<Endereco> findAll() {
        return Optional.of(enderecoRepository.findAll())
                .filter(enderecos -> !enderecos.isEmpty())
                .orElseThrow(() -> new EnderecoNotFoundException("Nenhuma Endereço encontrado.", System.currentTimeMillis()));
    }

    @Override
    public Endereco findById(int enderecoId) {
        return enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new EnderecoNotFoundException("Endereco com id " + enderecoId + " não foi encontrado.", System.currentTimeMillis()));
    }

    @Override
    public void saveEnderecoPessoa(Endereco endereco, int pessoaId) {
        Optional.ofNullable(endereco)
                .filter(e -> e.getLogradouro() != null
                        && !e.getLogradouro().isEmpty()
                        && !e.getCidade().isEmpty()
                        && e.getCidade() != null
                        && e.getNumero() > 0
                        && e.getCep() > 0)
                .ifPresentOrElse(e -> Optional.ofNullable(pessoaService.findById(pessoaId)).ifPresent(pessoa -> {
                    endereco.setId(0);
                    pessoa.setEndereco(endereco);
                    enderecoRepository.save(endereco);
                }), () -> {
                    throw new EnderecoBadRequestException("Endereço Inválido. Verifique os campos e tente novamente.", System.currentTimeMillis());
                });

    }

    @Override
    public void update(Optional<Endereco> endereco) {
        endereco.ifPresentOrElse(e -> {
            enderecoRepository.update(e);
        }, () -> {
            throw new EnderecoBadRequestException("JSON Inválido.", System.currentTimeMillis());
        });
    }

    @Override
    public void updateEnderecoPrincipal(int enderecoId, int pessoaId) {
        // Optional e Lambda quando eu pensar mais

        Pessoa pessoa = pessoaService.findById(pessoaId);
        Endereco endereco = findById(enderecoId);

        // Forçar 1 endereco principal
        pessoa.getEnderecos().stream()
                .filter(e -> e.getId() == enderecoId)
                .findFirst()
                .ifPresentOrElse(
                        e -> {
                            e.setEnderecoPrincipal(true);
                            pessoa.getEnderecos().stream()
                                    .filter(other -> other != e)
                                    .forEach(other -> other.setEnderecoPrincipal(false));
                            update(Optional.ofNullable(endereco));
                        },
                        () -> {
                            throw new EnderecoNotFoundException(
                                    String.format("Endereço de id '%d' não encontrado na lista de endereços da pessoa de id '%d'.",
                                            enderecoId,
                                            pessoaId),
                                    System.currentTimeMillis()
                            );
                        }
                );

    }
}
