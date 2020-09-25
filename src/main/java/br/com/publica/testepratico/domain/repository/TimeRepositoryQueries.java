package br.com.publica.testepratico.domain.repository;

import java.util.List;

import br.com.publica.testepratico.domain.filter.TimeFilter;
import br.com.publica.testepratico.domain.model.Time;

public interface TimeRepositoryQueries {

	public List<Time> filtrar(TimeFilter filter);
	
}
