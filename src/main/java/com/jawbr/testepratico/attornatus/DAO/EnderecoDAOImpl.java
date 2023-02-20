package com.jawbr.testepratico.attornatus.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jawbr.testepratico.attornatus.entity.Endereco;

import jakarta.persistence.EntityManager;

@Repository
public class EnderecoDAOImpl implements EnderecoDAO {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<Endereco> getAllEnderecos() {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Endereco> query = currentSession.createQuery("from Endereco", Endereco.class);
		
		List<Endereco> enderecos = query.getResultList();
		
		return enderecos;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void saveEndereco(Endereco endereco) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		currentSession.save(endereco);
	}

	@Override
	public Endereco getEndereco(int enderecoId) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Endereco theEndereco = currentSession.get(Endereco.class, enderecoId);
		
		return theEndereco;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateEndereco(Endereco theEndereco) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		currentSession.update(theEndereco);
	}

}
