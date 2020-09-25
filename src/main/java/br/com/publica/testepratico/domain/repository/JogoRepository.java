package br.com.publica.testepratico.domain.repository;

import org.springframework.stereotype.Repository;

import br.com.publica.testepratico.domain.model.Jogo;

@Repository
public interface JogoRepository extends CustomJpaRepository<Jogo, Long>, JogoRepositoryQueries {
	
}
