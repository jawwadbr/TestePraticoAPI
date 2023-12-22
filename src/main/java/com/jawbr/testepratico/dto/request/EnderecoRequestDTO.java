package com.jawbr.testepratico.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EnderecoRequestDTO(
        @NotBlank(message = "Logradouro não pode estar vazio!") String logradouro,
        @NotBlank(message = "Cep não pode estar vazio!") String cep,
        @Positive(message = "Número não pode estar vazio!") int numero,
        @NotBlank(message = "cidade não pode estar vazio!") String cidade,
        @NotNull(message = "Endereço principal não pode estar vazio!") boolean endereco_principal
) {
}
