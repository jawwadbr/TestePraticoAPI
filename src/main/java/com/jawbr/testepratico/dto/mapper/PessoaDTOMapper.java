package com.jawbr.testepratico.dto.mapper;

import com.jawbr.testepratico.dto.PessoaDTO;
import com.jawbr.testepratico.dto.request.EnderecoRequestDTO;
import com.jawbr.testepratico.dto.request.PessoaRequestDTO;
import com.jawbr.testepratico.entity.Endereco;
import com.jawbr.testepratico.entity.Pessoa;
import com.jawbr.testepratico.util.PATH;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class PessoaDTOMapper implements Function<Pessoa, PessoaDTO> {

    @Override
    public PessoaDTO apply(Pessoa pessoa) {
        String url = PATH.ConsultaPessoaEnderecoParamId.getUrl() + pessoa.getId();
        return new PessoaDTO(
                pessoa.getNome(),
                null,
                pessoa.getDataDeNascimento(),
                null,
                url
        );
    }

    public PessoaDTO applyWithEndereco(Pessoa pessoa) {

        List<PessoaDTO.EnderecoDTO> enderecos = new ArrayList<>();
        for(Endereco endereco : pessoa.getEnderecos()) {
            enderecos.add(new PessoaDTO.EnderecoDTO(
                    endereco.getLogradouro(),
                    endereco.getCep(),
                    endereco.getNumero(),
                    endereco.getCidade(),
                    endereco.isEnderecoPrincipal(),
                    null
            ));
        }

        return new PessoaDTO(
                pessoa.getNome(),
                null,
                null,
                enderecos,
                null
        );
    }

    public Pessoa DtoToEntityFromRequest(PessoaRequestDTO pessoaRequestDTO) {

        List<Endereco> enderecoList = new ArrayList<>();
        for(EnderecoRequestDTO enderecoDTO : pessoaRequestDTO.enderecos()) {
            Endereco e = new Endereco(enderecoDTO.logradouro(),
                    Long.parseLong(enderecoDTO.cep()),
                    enderecoDTO.numero(),
                    enderecoDTO.cidade());
            e.setEnderecoPrincipal(enderecoDTO.endereco_principal());
            enderecoList.add(e);
        }

        String dateString = pessoaRequestDTO.data_de_nascimento();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);

        return new Pessoa(pessoaRequestDTO.nome(),
                localDate,
                enderecoList);
    }
}
