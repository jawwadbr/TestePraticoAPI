package com.jawbr.testepratico.dto;

public record EnderecoDTO(
        String logradouro,
        long cep,
        int numero,
        String cidade,
        boolean endereco_principal
) {
}
