package com.jawbr.testepratico.service;

import com.jawbr.testepratico.dto.ListarPessoasDTO;
import com.jawbr.testepratico.dto.PessoaDTO;
import com.jawbr.testepratico.dto.ResponseEntityDTO;
import com.jawbr.testepratico.dto.mapper.ListarPessoasDTOMapper;
import com.jawbr.testepratico.dto.mapper.PessoaDTOMapper;
import com.jawbr.testepratico.dto.request.EnderecoRequestDTO;
import com.jawbr.testepratico.dto.request.PessoaRequestDTO;
import com.jawbr.testepratico.entity.Endereco;
import com.jawbr.testepratico.entity.Pessoa;
import com.jawbr.testepratico.entity.User;
import com.jawbr.testepratico.exception.InvalidParameterException;
import com.jawbr.testepratico.exception.MultiplePrincipalAddressExceptions;
import com.jawbr.testepratico.exception.PessoaNotFoundException;
import com.jawbr.testepratico.repository.PessoaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PessoaDTOMapper pessoaDTOMapper;
    private final UserService userService;
    private final ListarPessoasDTOMapper listarPessoasDTOMapper;

    public PessoaService(PessoaRepository pessoaRepository, PessoaDTOMapper pessoaDTOMapper, UserService userService, ListarPessoasDTOMapper listarPessoasDTOMapper) {
        this.pessoaRepository = pessoaRepository;
        this.pessoaDTOMapper = pessoaDTOMapper;
        this.userService = userService;
        this.listarPessoasDTOMapper = listarPessoasDTOMapper;
    }

    // Listar Pessoas
    public ListarPessoasDTO findAllPessoas() {
        return Optional.of(pessoaRepository.findAll())
                .filter(list -> !list.isEmpty())
                .map(listarPessoasDTOMapper)
                .orElseThrow(() -> new PessoaNotFoundException("Nenhuma pessoa encontrada"));
    }

    // Consulta de Pessoa usando Param
    public ResponseEntityDTO findPessoa(String nome, Integer id) {
        if(nome != null) {
            // Consultar uma pessoa, caso tenha mais de uma pessoa com mesmo nome
            List<PessoaDTO> pessoasByNome = Optional.of(pessoaRepository.findByNome(nome))
                    .filter(list -> !list.isEmpty())
                    .map(list -> list.stream().map(pessoaDTOMapper).toList())
                    .orElseThrow(() -> new PessoaNotFoundException(String.format("Pessoa(s) com nome '%s' não foi encontrada.", nome)));
            return new ResponseEntityDTO(pessoasByNome);
        }
        else if(id != null) {
            // Consultar uma pessoa específica
            PessoaDTO pessoaById = pessoaRepository.findById(id)
                    .map(pessoaDTOMapper)
                    .orElseThrow(() -> new PessoaNotFoundException(String.format("Pessoa com id '%o' não foi encontrada.", id)));
            return new ResponseEntityDTO(pessoaById);
        }

        throw new InvalidParameterException("Por favor insira o parametro 'nome' ou 'id' para a consulta de endereços.");
    }

    // listar endereços da pessoa usando Param
    public ResponseEntityDTO findPessoaEndereco(String nome, Integer id) {
        if(nome != null) {
            // Consultar endereco de uma pessoa, caso tenha mais de uma pessoa com mesmo nome
            List<PessoaDTO> pessoasEnderecosByNome = Optional.of(pessoaRepository.findByNome(nome))
                    .filter(list -> !list.isEmpty())
                    .map(list -> list.stream().map(pessoaDTOMapper::applyWithEndereco).toList())
                    .orElseThrow(() -> new PessoaNotFoundException(String.format("Pessoa(s) com nome '%s' não foi encontrada.", nome)));
            return new ResponseEntityDTO(pessoasEnderecosByNome);
        }
        else if(id != null) {
            PessoaDTO pessoasEnderecosById = pessoaRepository.findById(id)
                    .map(pessoaDTOMapper::applyWithEndereco)
                    .orElseThrow(() -> new PessoaNotFoundException(String.format("Pessoa com id '%o' não foi encontrada.", id)));
            return new ResponseEntityDTO(pessoasEnderecosById);
        }

        throw new InvalidParameterException("Por favor insira o parametro 'nome' ou 'id' para a consulta de endereços.");
    }

    // criar pessoa
    public PessoaDTO createPessoa(PessoaRequestDTO pessoaRequest) {
        doesPessoaHaveMultiplePrincipalAddress(pessoaRequest);

        Pessoa savedPessoa = pessoaRepository.save(pessoaDTOMapper.DtoToEntityFromRequest(pessoaRequest));
        return pessoaDTOMapper.apply(savedPessoa);
    }

    // editar pessoa
    public PessoaDTO updatePessoa(int id, PessoaRequestDTO pessoaRequest) {
        doesPessoaHaveMultiplePrincipalAddress(pessoaRequest);

        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() ->
                        new PessoaNotFoundException(String.format("Pessoa com id '%o' não foi encontrada.", id)));

        final String nomePessoa = StringUtils.hasText(pessoaRequest.nome()) ? pessoaRequest.nome() : pessoa.getNome();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate pessoaDate = pessoa.getDataDeNascimento();
        String formattedDate = pessoaDate.format(formatter);
        final String dataNascimentoPessoa = StringUtils.hasText(pessoaRequest.data_de_nascimento()) ? pessoaRequest.data_de_nascimento() : formattedDate;

        List<Endereco> enderecoList = new ArrayList<>();
        List<EnderecoRequestDTO> toCheckEnderecosList = new ArrayList<>();
        if(pessoaRequest.enderecos() != null) {
            toCheckEnderecosList.addAll(pessoaRequest.enderecos());
        }

        if(!toCheckEnderecosList.isEmpty()) {
            for(int i = 0; i < toCheckEnderecosList.size(); i++) {
                final String enderecoLogradouro = StringUtils.hasText(toCheckEnderecosList.get(i).logradouro()) ?
                        toCheckEnderecosList.get(i).logradouro() : pessoa.getEnderecos().get(i).getLogradouro();

                final String enderecoCep = StringUtils.hasText(String.valueOf(toCheckEnderecosList.get(i).cep())) ?
                        String.valueOf(toCheckEnderecosList.get(i).cep()) : String.valueOf(pessoa.getEnderecos().get(i).getCep());

                final String enderecoNumero = StringUtils.hasText(String.valueOf(toCheckEnderecosList.get(i).numero())) ?
                        String.valueOf(toCheckEnderecosList.get(i).numero()) : String.valueOf(pessoa.getEnderecos().get(i).getNumero());

                final String enderecoCidade = StringUtils.hasText(toCheckEnderecosList.get(i).cidade()) ?
                        toCheckEnderecosList.get(i).cidade() : pessoa.getEnderecos().get(i).getCidade();

                final String enderecoPrincipal = StringUtils.hasText(String.valueOf(toCheckEnderecosList.get(i).endereco_principal())) ?
                        String.valueOf(toCheckEnderecosList.get(i).endereco_principal()) : String.valueOf(pessoa.getEnderecos().get(i).isEnderecoPrincipal());

                Endereco enderecoPessoa = new Endereco();
                enderecoPessoa.setLogradouro(enderecoLogradouro);
                enderecoPessoa.setCep(Long.parseLong(enderecoCep));
                enderecoPessoa.setNumero(Integer.parseInt(enderecoNumero));
                enderecoPessoa.setCidade(enderecoCidade);
                enderecoPessoa.setEnderecoPrincipal(Boolean.parseBoolean(enderecoPrincipal));
                enderecoList.add(enderecoPessoa);
            }
        }
        else {
            enderecoList.addAll(pessoa.getEnderecos());
        }

        Pessoa updatedPessoa = new Pessoa();
        updatedPessoa.setId(pessoa.getId());
        updatedPessoa.setNome(nomePessoa);
        LocalDate localDate = LocalDate.parse(dataNascimentoPessoa, formatter);
        updatedPessoa.setDataDeNascimento(localDate);
        updatedPessoa.setEnderecos(enderecoList);

        pessoaRepository.save(updatedPessoa);

        return pessoaDTOMapper.apply(updatedPessoa);
    }

    public void deleteById(int pessoaId) {
        pessoaRepository.findById(pessoaId).ifPresentOrElse(
                pessoaRepository::delete, () -> {
                    throw new PessoaNotFoundException("Pessoa com id " + pessoaId + " não encontrada.");
                }
        );
    }

    private void doesPessoaHaveMultiplePrincipalAddress(PessoaRequestDTO pessoaRequestDTO) {
        int doesPessoaHasMoreThanOneEnderecoPrincipal = 0;
        for(EnderecoRequestDTO enderecoRequestDTO : pessoaRequestDTO.enderecos()) {
            if(enderecoRequestDTO.endereco_principal()) {
                doesPessoaHasMoreThanOneEnderecoPrincipal++;
            }
        }
        if(doesPessoaHasMoreThanOneEnderecoPrincipal > 1) {
            throw new MultiplePrincipalAddressExceptions("Pessoa possui mais de um endereço principal.");
        }
    }

    @PostConstruct
    void init() {
//        Pessoa p = new Pessoa();
//        p.setNome("test");
//        p.setDataDeNascimento(LocalDate.of(1999, 4, 9));
//        p.addEndereco(new Endereco("Vila D'ouro", 12345678, 230, "São Paulo"));
//        p.addEndereco(new Endereco("Vila D'ouro2", 12345678, 230, "São Paulo2"));
//
//        pessoaRepository.save(p);
//
//        Pessoa p2 = new Pessoa();
//        p2.setNome("test");
//        p2.setDataDeNascimento(LocalDate.of(1995, 4, 9));
//        p2.addEndereco(new Endereco("Vila D'ouro2", 12345678, 230, "São Paulo2"));
//
//        pessoaRepository.save(p2);

        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setActive(true);
        user.setRole("ROLE_ADMIN");

        userService.save(user);

    }
}