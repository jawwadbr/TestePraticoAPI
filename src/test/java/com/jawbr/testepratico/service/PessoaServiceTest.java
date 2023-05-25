package com.jawbr.testepratico.service;

import com.jawbr.testepratico.DAO.PessoaRepository;
import com.jawbr.testepratico.customException.PessoaNotFoundException;
import com.jawbr.testepratico.entity.Pessoa;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestPropertySource("/application.properties")
@SpringBootTest
class PessoaServiceTest { // TESTES INCOMPLETOS - Finalizar ainda

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private PessoaRepository pessoaRepository;

    @BeforeEach
    void setup() {
        jdbc.execute("INSERT INTO pessoa (id, nome, data_nascimento) " +
                " values (2,'Bradley', '1998-04-01')");
    }


    @Test
    void findAll() {

        List<Pessoa> result = pessoaService.findAll();

        assertEquals(1, result.size());
        assertEquals("Bradley", result.get(0).getNome());

    }

    @Test
    void findById() {

        System.out.println(pessoaRepository.findAll());
        Optional<Pessoa> result = pessoaRepository.findById(2);

        assertEquals("Bradley", result.get().getNome(), "Id deveria de existir");
    }

    @Test
    void cannotFindByIdAndThrowsException() {

        Exception exc = assertThrows(PessoaNotFoundException.class,
                () -> pessoaService.findById(10));

        String expectedMessage = "Pessoa com id 10 não encontrada.";
        String actualMessage = exc.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void savePessoa() {
        Pessoa pessoa = new Pessoa("Mariana", LocalDate.of(1994, 4, 1));

        pessoaService.save(pessoa);

        Optional<Pessoa> result = pessoaRepository.findByNome("Mariana");
        System.out.println(result);
        assertTrue(result.isPresent());
        assertEquals("Mariana", result.get().getNome());
    }



    @Test
    @Disabled
    void cannotSavePessoa() {
    }

    @Test
    @Disabled
    void update() {
    }

    @Test
    @Disabled
    void delete() {
    }

    @AfterEach
    void setupAfterEach() {
        jdbc.execute("DELETE FROM pessoa");
    }

}