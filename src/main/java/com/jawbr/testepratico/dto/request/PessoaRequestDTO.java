package com.jawbr.testepratico.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record PessoaRequestDTO(
        @NotBlank(message = "Nome não pode estar vazio!") String nome,
        @NotBlank(message = "Data de Nascimento não pode estar vazio!") String data_de_nascimento,
        List<EnderecoRequestDTO> enderecos
) {
}
