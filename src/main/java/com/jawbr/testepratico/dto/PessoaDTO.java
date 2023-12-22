package com.jawbr.testepratico.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PessoaDTO(
        String nome,
        Integer identificador,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate data_de_nascimento,
        List<EnderecoDTO> enderecos,
        String url_de_consulta_endereco
) {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static record EnderecoDTO(
            String logradouro,
            long cep,
            int numero,
            String cidade,
            boolean endereco_principal,
            Integer identificador
    ) {
    }
}
