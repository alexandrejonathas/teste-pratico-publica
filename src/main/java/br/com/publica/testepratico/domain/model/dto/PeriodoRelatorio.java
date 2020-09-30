package br.com.publica.testepratico.domain.model.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeriodoRelatorio {

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataInicio;
	
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataFim;
	
}
