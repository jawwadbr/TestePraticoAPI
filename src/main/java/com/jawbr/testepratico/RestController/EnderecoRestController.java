package com.jawbr.testepratico.RestController;

import com.jawbr.testepratico.entity.Endereco;
import com.jawbr.testepratico.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoRestController {

    private final EnderecoService enderecoService;

    @Autowired
    public EnderecoRestController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    // Endpoint PUT "/pessoas/{pessoaId}" - CRIAR ENDEREÇO PARA PESSOA
    @PutMapping("/{pessoaId}")
    public ResponseEntity<Void> saveEnderecoPessoa(@RequestBody Endereco endereco, @PathVariable int pessoaId) {
        enderecoService.saveEnderecoPessoa(endereco, pessoaId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Endpoint PUT - PODER INFORMAR QUAL ENDEREÇO É O PRINCIPAL DA PESSOA
    @PutMapping("/{pessoaId}/{enderecoId}")
    public ResponseEntity<Void> setEnderecoPrincipal(@PathVariable int pessoaId, @PathVariable int enderecoId) {
        enderecoService.updateEnderecoPrincipal(enderecoId, pessoaId);
        return ResponseEntity.noContent().build();
    }

}
