package br.com.publica.testepratico.infrastructure.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import br.com.publica.testepratico.domain.repository.CustomJpaRepository;


public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID> {

	@Autowired
	private EntityManager manager;

	public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, 
			EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.manager = entityManager;
	}

	@Override
	public Optional<T> buscarPrimeiro() {
		String jpql = "from "+ getDomainClass().getName();
		
		T entity = manager.createQuery(jpql, getDomainClass())
					.setMaxResults(1)
					.getSingleResult();
		
		return Optional.ofNullable(entity);
	}

	@Override
	public void detach(T entity) {
		manager.detach(entity);
	}

}
