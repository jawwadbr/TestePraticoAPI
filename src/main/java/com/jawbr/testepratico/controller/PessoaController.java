package com.jawbr.testepratico.controller;

import com.jawbr.testepratico.dto.ListarPessoasDTO;
import com.jawbr.testepratico.dto.PessoaDTO;
import com.jawbr.testepratico.dto.ResponseEntityDTO;
import com.jawbr.testepratico.dto.request.PessoaRequestDTO;
import com.jawbr.testepratico.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pessoa")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping
    public ListarPessoasDTO findAllPessoas() {
        return pessoaService.findAllPessoas();
    }

    @GetMapping("/consulta")
    public ResponseEntityDTO findPessoa(@RequestParam(name = "nome", required = false) String nome,
                                        @RequestParam(name = "id", required = false) Integer id)
    {
        return pessoaService.findPessoa(nome, id);
    }

    @GetMapping("/consulta/endereco")
    public ResponseEntityDTO findPessoaEndereco(@RequestParam(name = "nome", required = false) String nome,
                                                @RequestParam(name = "id", required = false) Integer id)
    {
        return pessoaService.findPessoaEndereco(nome, id);
    }

    @PostMapping
    public PessoaDTO createPessoa(@Valid @RequestBody PessoaRequestDTO pessoaRequestDTO) {
        return pessoaService.createPessoa(pessoaRequestDTO);
    }

    // editar pessoa
    @PatchMapping("/{id}")
    public PessoaDTO updatePessoaById(@RequestBody PessoaRequestDTO pessoaRequestDTO, @PathVariable int id) {
        return pessoaService.updatePessoa(id, pessoaRequestDTO);
    }

    // deletar pessoa
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePessoaById(@PathVariable int id) {
        pessoaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
