package br.com.publica.testepratico.domain.filter;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PontuacaoFilter {

	private LocalDate dataInicio;

	private LocalDate dataFim;
	
	private String timeCasa;
	
	private String timeVisitante;
	
}
