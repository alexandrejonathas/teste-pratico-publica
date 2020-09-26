package br.com.publica.testepratico.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.publica.testepratico.domain.model.Time;

@Repository
public interface TimeRepository extends CustomJpaRepository<Time, Long>, TimeRepositoryQueries {
	
	List<Time> findByNomeLike(String nome);
	
	Time findByNomeIgnoreCase(String nome);
	
}
