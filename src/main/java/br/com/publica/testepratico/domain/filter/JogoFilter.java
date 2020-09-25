package br.com.publica.testepratico.domain.filter;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JogoFilter {

	private OffsetDateTime data;
	
	private String nome;
	
}
