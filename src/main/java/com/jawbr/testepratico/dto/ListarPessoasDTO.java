package com.jawbr.testepratico.dto;

import java.util.List;

public record ListarPessoasDTO(
        List<PessoaEntityReferenceDTO> pessoas
) {
    public static record PessoaEntityReferenceDTO(
            String nome,
            String url_de_consulta
    ) {
    }
}
