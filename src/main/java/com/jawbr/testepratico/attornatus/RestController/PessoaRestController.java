package com.jawbr.testepratico.attornatus.RestController;

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
import com.jawbr.testepratico.attornatus.customException.PessoaNotFoundException;
import com.jawbr.testepratico.attornatus.entity.Endereco;
import com.jawbr.testepratico.attornatus.entity.Pessoa;
import com.jawbr.testepratico.attornatus.jsonViews.PessoaComEndereco;
import com.jawbr.testepratico.attornatus.jsonViews.PessoaSemEndereco;
import com.jawbr.testepratico.attornatus.service.EnderecoService;
import com.jawbr.testepratico.attornatus.service.PessoaService;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api")
public class PessoaRestController {

	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private EnderecoService enderecoService;
	
	// Endpoint para GET "/pessoas" | LISTAR PESSOAS
	@JsonView(PessoaSemEndereco.class)
	@GetMapping("/pessoas")
	public List<Pessoa> getPessoas() {
		return pessoaService.getAllPessoas();
	}
	
	// Endpoint GET "/pessoas/{pessoaId}" - Pegar Pessoa especifica e listar endereços | CONSULTAR PESSOAS e LISTAR ENDEREÇOS DA PESSOA
	@JsonView(PessoaComEndereco.class)
	@GetMapping("/pessoas/{pessoaId}")
	public Pessoa getPessoa(@PathVariable int pessoaId) {
		
		Pessoa thePessoa = pessoaService.getPessoa(pessoaId);
		
		if(thePessoa == null) {
			throw new PessoaNotFoundException("Pessoa não encontrada - " + pessoaId);
		}
		
		return thePessoa;
	}
	
	// ATENCÃO É NECESSARIO COLOCAR O NOME COMPLETO E É CASE SENSITIVE
	@GetMapping("/pessoas/nome/{nomePessoa}")
	public Pessoa getPessoa(@PathVariable String nomePessoa) {
		
		Pessoa thePessoa = pessoaService.getPessoa(nomePessoa);
		
		if(thePessoa == null) {
			throw new PessoaNotFoundException("Pessoa não encontrada - " + nomePessoa); // TRATAR A EXCEPTIONS COM UMA CUSTOMIZADA
		}
		
		return thePessoa;
	}
	
	// Endpoint POST "/pessoas" - Criar Pessoa | CRIAR UMA PESSOA
	@PostMapping("/pessoas")
	public Pessoa savePessoa(@RequestBody Pessoa thePessoa) {
		
		pessoaService.savePessoa(thePessoa);
		
		return thePessoa;
	}
	
	// Endpoint PUT "/pessoas" - Update Pessoa | EDITAR UMA PESSOA
	@PutMapping("/pessoas")
	public Pessoa updatePessoa(@RequestBody Pessoa thePessoa) {
		
		pessoaService.updatePessoa(thePessoa);
		
		return thePessoa;
	}
	
	// Endpoint DELETE "/pessoas/{pessoaId}" - Deletar Pessoa por ID | ---NÃO TEM NO TESTE---
	@DeleteMapping("/pessoas/{pessoaId}")
	public String deletePessoa(@PathVariable int pessoaId) {
		
		Pessoa thePessoa = pessoaService.getPessoa(pessoaId);
		
		if(thePessoa == null) {
			throw new PessoaNotFoundException("Pessoa não encontrada - " + pessoaId); // TRATAR A EXCEPTIONS COM UMA CUSTOMIZADA
		}
		
		pessoaService.deletePessoa(thePessoa);
		
		return thePessoa.getNome() + " foi Deletado(a)!";
	}
	
	// Endpoint PUT "/pessoas/{pessoaId}" - CRIAR ENDEREÇO PARA PESSOA
	@PutMapping("/pessoas/{pessoaId}")
	public Pessoa saveEnderecoPessoa(@RequestBody Endereco theEndereco, @PathVariable int pessoaId) {
		
		Pessoa thePessoa = pessoaService.getPessoa(pessoaId);
		
		if(thePessoa == null) {
			throw new PessoaNotFoundException("Pessoa não encontrada - " + pessoaId); // TRATAR A EXCEPTIONS COM UMA CUSTOMIZADA
		}
		
		thePessoa.addEndereco(theEndereco);
		
		pessoaService.updatePessoa(thePessoa);
		
		return thePessoa;
	}
	
	// PODER INFORMAR QUAL ENDEREÇO É O PRINCIPAL DA PESSOA
	@PutMapping("/pessoas/{pessoaId}/endereco/{enderecoId}")
	public Pessoa setEnderecoPrincipal(@PathVariable int pessoaId, @PathVariable int enderecoId) {
		
		Pessoa thePessoa = pessoaService.getPessoa(pessoaId);
		
		Endereco theEndereco = enderecoService.getEndereco(enderecoId);
		
		if(thePessoa == null) {
			throw new PessoaNotFoundException("Pessoa não encontrada - " + pessoaId);
		}
		if(theEndereco == null) {
			throw new PessoaNotFoundException("Endereço não encontrado - " + enderecoId);
		}
		
		// Forçar apenas 1 endereço principal
		List<Endereco> tempEnderecos = thePessoa.getEnderecos();
		for (Endereco e : tempEnderecos) {
			if(e.getId() == enderecoId && !e.isEnderecoPrincipal()) {
				e.setEnderecoPrincipal(true);
			}
			else {
				e.setEnderecoPrincipal(false);
			}
		}
		
		enderecoService.updateEndereco(theEndereco);
		pessoaService.updatePessoa(thePessoa);
		
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
		
		pessoaService.savePessoa(p1);
		pessoaService.savePessoa(p2);
		pessoaService.savePessoa(p3);
		pessoaService.savePessoa(p4);
	}
	
}
