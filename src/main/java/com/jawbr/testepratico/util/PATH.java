package com.jawbr.testepratico.util;

public enum PATH {
    ConsultaPessoaParamId("/api/pessoa/consulta?id="),
    ConsultaPessoaEnderecoParamId("/api/pessoa/consulta/endereco?id=");

    private final String url;

    PATH(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
