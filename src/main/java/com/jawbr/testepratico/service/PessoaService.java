package com.jawbr.testepratico.service;

import com.jawbr.testepratico.DAO.PessoaRepository;
import com.jawbr.testepratico.customException.PessoaBadRequestException;
import com.jawbr.testepratico.customException.PessoaNotFoundException;
import com.jawbr.testepratico.entity.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    // MELHORAR SAVE e UPDATE

    public List<Pessoa> findAll() {
        return Optional.of(pessoaRepository.findAll())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new PessoaNotFoundException("Nenhuma Pessoa encontrada dentro do banco de dados.", System.currentTimeMillis()));
    }

    public Pessoa findById(int pessoaId) {
        return pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new PessoaNotFoundException("Pessoa com id " + pessoaId + " não encontrada.", System.currentTimeMillis()));
    }

    public Pessoa findByNome(String pessoaNome) {
        return pessoaRepository.findByNome(pessoaNome)
                .orElseThrow(() -> new PessoaNotFoundException("Pessoa com nome " + pessoaNome + " não encontrada.", System.currentTimeMillis()));
    }

    public void save(Pessoa pessoa) {
        pessoa.setId(0);
        pessoaRepository.save(pessoa);
    }

    public void update(Optional<Pessoa> pessoa) {
        pessoa.ifPresent(p -> pessoaRepository.findById(p.getId())
                .filter(pessoaDB -> p.getNome() != null && !p.getNome().isEmpty() && p.getDataDeNascimento() != null)
                .orElseThrow(() -> {
                    if(p.getNome() == null || p.getNome().isEmpty()) {
                        return new PessoaBadRequestException("O nome da pessoa não pode estar vazio ou nulo.", System.currentTimeMillis());
                    } else {
                        return new PessoaNotFoundException("Pessoa com id " + p.getId() + " não encontrada.", System.currentTimeMillis());
                    }
                })
        );
        pessoa.ifPresent(pessoaRepository::update);

    }

    public void delete(int pessoaId) {
        pessoaRepository.findById(pessoaId).ifPresentOrElse(
                pessoaRepository::delete, () -> {
                    throw new PessoaNotFoundException("Pessoa com id " + pessoaId + " não encontrada.", System.currentTimeMillis());
                }
        );
    }
}