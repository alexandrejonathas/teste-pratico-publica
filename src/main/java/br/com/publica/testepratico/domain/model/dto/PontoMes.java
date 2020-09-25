package br.com.publica.testepratico.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PontoMes {
	
	private String mes;
	
	private Integer total;
}
