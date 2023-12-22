package com.jawbr.testepratico.dto.request;

public record EnderecoRequestDTO(
        String logradouro,
        long cep,
        int numero,
        String cidade,
        boolean endereco_principal
) {
}
