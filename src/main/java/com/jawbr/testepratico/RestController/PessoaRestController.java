package com.jawbr.testepratico.RestController;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.jawbr.testepratico.customException.EnderecoNotFoundException;
import com.jawbr.testepratico.customException.PessoaNotFoundException;
import com.jawbr.testepratico.entity.Endereco;
import com.jawbr.testepratico.entity.Pessoa;
import com.jawbr.testepratico.jsonViews.PessoaComEndereco;
import com.jawbr.testepratico.jsonViews.PessoaSemEndereco;
import com.jawbr.testepratico.service.EnderecoService;
import com.jawbr.testepratico.service.PessoaService;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaRestController {

	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private EnderecoService enderecoService;
	
	// Endpoint para GET "/pessoas" | LISTAR PESSOAS
	@JsonView(PessoaSemEndereco.class)
	@GetMapping()
	public List<Pessoa> getPessoas() {
		return pessoaService.findAll();
	}
	
	// Endpoint GET "/pessoas/{pessoaId}" - Pegar Pessoa especifica e listar endereços | CONSULTAR PESSOAS e LISTAR ENDEREÇOS DA PESSOA
	@JsonView(PessoaComEndereco.class)
	@GetMapping("/{pessoaId}")
	public Pessoa getPessoa(@PathVariable int pessoaId) {
		
		Pessoa thePessoa = pessoaService.findById(pessoaId);
		
		if(thePessoa == null) {
			throw new PessoaNotFoundException("Pessoa não encontrada - " + pessoaId);
		}
		
		return thePessoa;
	}
	
	// ATENCÃO É NECESSARIO COLOCAR O NOME COMPLETO E É CASE SENSITIVE
	@GetMapping("/nome/{nomePessoa}")
	public Pessoa getPessoa(@PathVariable String nomePessoa) {
		
		Pessoa thePessoa = pessoaService.findByNome(nomePessoa);
		
		if(thePessoa == null) {
			throw new PessoaNotFoundException("Pessoa não encontrada - " + nomePessoa);
		}
		
		return thePessoa;
	}
	
	// Endpoint POST "/pessoas" - Criar Pessoa | CRIAR UMA PESSOA
	@PostMapping()
	public Pessoa savePessoa(@RequestBody Pessoa thePessoa) {
		
		// Forçar a criar uma nova Pessoa
		thePessoa.setId(0);
		
		pessoaService.save(thePessoa);
		
		return thePessoa;
	}
	
	// Endpoint PUT "/pessoas" - Update Pessoa | EDITAR UMA PESSOA
	@PutMapping()
	public Pessoa updatePessoa(@RequestBody Pessoa thePessoa) {
		
		pessoaService.save(thePessoa);
		
		return thePessoa;
	}
	
	// Endpoint DELETE "/pessoas/{pessoaId}" - Deletar Pessoa por ID | ---NÃO TEM NO TESTE---
	@DeleteMapping("/{pessoaId}")
	public String deletePessoa(@PathVariable int pessoaId) {
		
		Pessoa thePessoa = pessoaService.findById(pessoaId);
		
		if(thePessoa == null) {
			throw new PessoaNotFoundException("Pessoa não encontrada - " + pessoaId);
		}
		
		pessoaService.delete(thePessoa);
		
		return thePessoa.getNome() + " foi Deletado(a)!";
	}
	
	// Endpoint PUT "/pessoas/{pessoaId}" - CRIAR ENDEREÇO PARA PESSOA
	@PutMapping("/{pessoaId}")
	public Pessoa saveEnderecoPessoa(@RequestBody Endereco theEndereco, @PathVariable int pessoaId) {
		
		Pessoa thePessoa = pessoaService.findById(pessoaId);
		
		if(thePessoa == null) {
			throw new PessoaNotFoundException("Pessoa não encontrada - " + pessoaId);
		}
		
		thePessoa.addEndereco(theEndereco);
		
		pessoaService.save(thePessoa);
		
		return thePessoa;
	}
	
	// PODER INFORMAR QUAL ENDEREÇO É O PRINCIPAL DA PESSOA
	@PutMapping("/{pessoaId}/endereco/{enderecoId}")
	public Pessoa setEnderecoPrincipal(@PathVariable int pessoaId, @PathVariable int enderecoId) {
		
		Pessoa thePessoa = pessoaService.findById(pessoaId);
		
		Endereco theEndereco = enderecoService.findById(enderecoId);
		
		if(thePessoa == null) {
			throw new PessoaNotFoundException("Pessoa não encontrada - " + pessoaId);
		}
		if(theEndereco == null) {
			throw new EnderecoNotFoundException("Endereço não encontrado - " + enderecoId);
		}
		
		// Forçar apenas 1 endereço principal
		List<Endereco> tempEnderecos = thePessoa.getEnderecos();
		boolean foundEndereco = false;
		for (Endereco endereco : tempEnderecos) {
			if(endereco.getId() == enderecoId && !endereco.isEnderecoPrincipal()) {
				foundEndereco = true;
				endereco.setEnderecoPrincipal(true);
			}
			else {
				endereco.setEnderecoPrincipal(false);
			}
		}
		
		if(!foundEndereco) {
			throw new EnderecoNotFoundException(String.format("Endereço de id '%d' não encontrado na lista de endereços da pessoa de id '%d'.", enderecoId, pessoaId));
		}
		
		enderecoService.save(theEndereco);
		pessoaService.save(thePessoa);
		
		return thePessoa;
	}
	
	
	// METODO PARA TESTES
	
	// PostConstruct popular DB e fazer testes - INFORMAÇÕES GENERICAS
	@PostConstruct
	private void popularDB() throws ParseException {
		
		Pessoa p1 = new Pessoa("John Doe", LocalDate.of(1999, 4, 9));
		Pessoa p2 = new Pessoa("Jane Doe", LocalDate.of(1979, 1, 22));
		Pessoa p3 = new Pessoa("Thor Filho", LocalDate.of(1999, 10, 10));
		Pessoa p4 = new Pessoa("Mariana Silva", LocalDate.of(2009, 4, 25));
		
		p1.addEndereco(new Endereco("Vila D'ouro", 12345678, 230, "São Paulo"));
		p1.addEndereco(new Endereco("Rua Augusto, Av de Lorem", 87654321, 230, "Porto Alegre"));
		
		p2.addEndereco(new Endereco("Rua Augusto, Av de Lorem", 87654321, 324, "Porto Alegre"));
		p2.addEndereco(new Endereco("Vilina Dali, Lorem Ipsum", 16097535, 126, "Restinga"));
		
		p3.addEndereco(new Endereco("Rua Peixoto, Morada III", 98765431, 210, "Gravatai"));
		
		pessoaService.save(p1);
		pessoaService.save(p2);
		pessoaService.save(p3);
		pessoaService.save(p4);
	}
	
}
