package com.jawbr.testepratico.controller;

import com.jawbr.testepratico.dto.ResponseEntityDTO;
import com.jawbr.testepratico.service.EnderecoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    // Consultar endereco de pessoa
    @GetMapping("endereco/consulta")
    public ResponseEntityDTO findEnderecoFromPessoa(@RequestParam(name = "nome", required = false) String nome,
                                                    @RequestParam(name = "id", required = false) Integer id)
    {
        return enderecoService.findEnderecoFromPessoa(nome, id);
    }

    // Consultar endereço principal da pessoa
    @GetMapping("/pessoa/{pessoaId}/endereco-principal")
    public ResponseEntityDTO findEnderecosPrincipalFromPessoa(@PathVariable int pessoaId) {
        return enderecoService.findEnderecosPrincipalFromPessoa(pessoaId);
    }

    // Informar qual endereco é o principal
    @PutMapping("/endereco/{pessoaId}/{enderecoId}")
    public ResponseEntity<Void> setEnderecoPrincipal(@PathVariable int pessoaId, @PathVariable int enderecoId) {
        enderecoService.updateEnderecoPrincipal(enderecoId, pessoaId);
        return ResponseEntity.noContent().build();
    }
}
