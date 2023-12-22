package com.jawbr.testepratico.dto.mapper;

import com.jawbr.testepratico.dto.ListarPessoasDTO;
import com.jawbr.testepratico.entity.Pessoa;
import com.jawbr.testepratico.util.PATH;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class ListarPessoasDTOMapper implements Function<List<Pessoa>, ListarPessoasDTO> {

    @Override
    public ListarPessoasDTO apply(List<Pessoa> pessoas) {

        List<ListarPessoasDTO.PessoaEntityReferenceDTO> listPessoa = new ArrayList<>();
        for(Pessoa p : pessoas) {
            listPessoa.add(new ListarPessoasDTO.PessoaEntityReferenceDTO(p.getNome(), PATH.ConsultaPessoaParamId.getUrl() + p.getId()));
        }

        return new ListarPessoasDTO(listPessoa);
    }
}
