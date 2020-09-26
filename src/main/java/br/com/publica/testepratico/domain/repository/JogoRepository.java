package br.com.publica.testepratico.domain.repository;

import java.time.LocalDate;

import org.springframework.stereotype.Repository;

import br.com.publica.testepratico.domain.model.Jogo;
import br.com.publica.testepratico.domain.model.Time;

@Repository
public interface JogoRepository extends CustomJpaRepository<Jogo, Long>, JogoRepositoryQueries {
	
	public Jogo findByDataAndTimeCasaAndTimeVisitante(LocalDate data, Time timeCasa, Time timeVisitante);
	
}
