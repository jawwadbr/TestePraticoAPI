package com.jawbr.testepratico.RestController;

import com.jawbr.testepratico.entity.Endereco;
import com.jawbr.testepratico.entity.Pessoa;
import com.jawbr.testepratico.service.EnderecoService;
import com.jawbr.testepratico.service.PessoaService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaRestController {

    private final PessoaService pessoaService;

    private final EnderecoService enderecoService;

    @Autowired
    public PessoaRestController(PessoaService pessoaService, EnderecoService enderecoService) {
        this.pessoaService = pessoaService;
        this.enderecoService = enderecoService;
    }

    // Endpoint para GET "/pessoas" | LISTAR PESSOAS
    @GetMapping()
    public List<Pessoa> getAllPessoas() {
        return pessoaService.findAll();
    }

    // Endpoint GET "/pessoas/{pessoaId}" - Pegar Pessoa especifica e listar endereços | CONSULTAR PESSOAS e LISTAR ENDEREÇOS DA PESSOA
    @GetMapping("/{pessoaId}")
    public Pessoa getPessoa(@PathVariable int pessoaId) {
        return pessoaService.findById(pessoaId);
    }

    // Endpoint POST "/pessoas" - Criar Pessoa | CRIAR UMA PESSOA
    @PostMapping()
    public ResponseEntity<Integer> savePessoa(@RequestBody Pessoa pessoa) {
        pessoaService.save(pessoa);
        return new ResponseEntity<>(pessoa.getId(), HttpStatus.CREATED);
    }

    // Endpoint PUT "/pessoas" - Update Pessoa | EDITAR UMA PESSOA
    @PutMapping()
    public ResponseEntity<Integer> updatePessoa(@RequestBody Optional<Pessoa> pessoa) {
        pessoaService.update(pessoa);
        pessoa.get().getEnderecos().forEach(endereco -> enderecoService.update(Optional.ofNullable(endereco)));
        return new ResponseEntity<>(pessoa.get().getId(), HttpStatus.OK);
    }

    // Endpoint DELETE "/pessoas/{pessoaId}" - Deletar Pessoa por ID | ---NÃO TEM NO TESTE---
    @DeleteMapping("/{pessoaId}")
    public ResponseEntity<Void> deletePessoa(@PathVariable int pessoaId) {
        pessoaService.delete(pessoaId);
        return ResponseEntity.noContent().build();
    }


    // METODO PARA TESTES

    // PostConstruct popular DB e fazer testes - INFORMAÇÕES GENERICAS
//    @PostConstruct
//    private void popularDB() throws ParseException {
//
//        Pessoa p1 = new Pessoa("John Doe", LocalDate.of(1999, 4, 9));
//        Pessoa p2 = new Pessoa("Jane Doe", LocalDate.of(1979, 1, 22));
//        Pessoa p3 = new Pessoa("Thor Filho", LocalDate.of(1999, 10, 10));
//        Pessoa p4 = new Pessoa("Mariana Silva", LocalDate.of(2009, 4, 25));
//
//        p1.addEndereco(new Endereco("Vila D'ouro", 12345678, 230, "São Paulo"));
//        p1.addEndereco(new Endereco("Rua Augusto, Av de Lorem", 87654321, 230, "Porto Alegre"));
//
//        p2.addEndereco(new Endereco("Rua Augusto, Av de Lorem", 87654321, 324, "Porto Alegre"));
//        p2.addEndereco(new Endereco("Vilina Dali, Lorem Ipsum", 16097535, 126, "Restinga"));
//
//        p3.addEndereco(new Endereco("Rua Peixoto, Morada III", 98765431, 210, "Gravatai"));
//
//        pessoaService.save(p1);
//        pessoaService.save(p2);
//        pessoaService.save(p3);
//        pessoaService.save(p4);
//    }

}
