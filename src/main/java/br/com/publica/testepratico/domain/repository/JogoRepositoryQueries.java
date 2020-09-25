package br.com.publica.testepratico.domain.repository;

import java.util.List;

import br.com.publica.testepratico.domain.filter.JogoFilter;
import br.com.publica.testepratico.domain.model.Jogo;

public interface JogoRepositoryQueries {

	public List<Jogo> filtrar(JogoFilter filter);
	
}
