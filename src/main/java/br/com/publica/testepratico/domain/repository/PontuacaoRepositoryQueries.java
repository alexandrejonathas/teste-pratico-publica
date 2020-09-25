package br.com.publica.testepratico.domain.repository;

import java.util.List;

import br.com.publica.testepratico.domain.filter.PontuacaoFilter;
import br.com.publica.testepratico.domain.model.Pontuacao;
import br.com.publica.testepratico.domain.model.dto.PontoMes;
import br.com.publica.testepratico.domain.model.dto.RecordeQuebrado;

public interface PontuacaoRepositoryQueries {

	public List<Pontuacao> filtrar(PontuacaoFilter filter);

	public RecordeQuebrado getQuantidadeRecordeQuebrados(); 
	
	public List<PontoMes> getTotalPontosPorMes();
	
}
