package br.com.publica.testepratico.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.publica.testepratico.domain.model.Pontuacao;

@Repository
public interface PontuacaoRepository extends JpaRepository<Pontuacao, Long>, PontuacaoRepositoryQueries {

	@Query("select min(minimoTemporada) from Pontuacao")
	public Integer getMinimoTemporada();
	
	@Query("select max(maximoTemporada) from Pontuacao")	
	public Integer getMaximoTemporada();		
	
}
