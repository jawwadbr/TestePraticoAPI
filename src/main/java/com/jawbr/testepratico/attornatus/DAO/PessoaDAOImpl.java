package com.jawbr.testepratico.attornatus.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jawbr.testepratico.attornatus.entity.Pessoa;

import jakarta.persistence.EntityManager;

@Repository
public class PessoaDAOImpl implements PessoaDAO {

	// Entity Manager para Sessão Hibernate
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<Pessoa> getAllPessoas() {
		
		// Pegar sessão atual
		Session currentSession = entityManager.unwrap(Session.class);
		
		// Query dentro da tabela
		Query<Pessoa> query = currentSession.createQuery("from Pessoa", Pessoa.class);
		
		// Result List e então return
		List<Pessoa> pessoas = query.getResultList();
		
		return pessoas;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void savePessoa(Pessoa pessoa) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		currentSession.save(pessoa);
	}

	@Override
	public Pessoa getPessoa(int pessoaId) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Pessoa thePessoa = currentSession.get(Pessoa.class, pessoaId);
		
		return thePessoa;
	}

	@Override
	public Pessoa getPessoa(String nomePessoa) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Pessoa> query = currentSession.createQuery("from Pessoa where nome like :nomePessoa", Pessoa.class);
		
		query.setParameter("nomePessoa", nomePessoa);
		
		Pessoa thePessoa = query.getSingleResult();
		
		return thePessoa;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void updatePessoa(Pessoa thePessoa) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		currentSession.update(thePessoa);
	}

	@Override
	public void deletePessoa(Pessoa thePessoa) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		currentSession.remove(thePessoa);
		
	}

}
