package br.com.publica.testepratico.domain.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa um registro na tabela <b>tbl_jogo</b> e um objeto jogo no projeto java.
 * @author Jonathas Lima
 * @version 1.0
 * @since Release 01 da aplicação
 */

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tbl_jogo")
public class Jogo {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")	
	private Long id;	
	
	@NotNull
	@Column(columnDefinition = "date")
	private LocalDate data;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "time_casa")
	private Time timeCasa;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "time_visitante")
	private Time timeVisitante;	
	
	/**
	 * Esse método é responsável por dizer se um objeto é novo ou não.
	 * @return boolean Se é um novo registro ou não
	 */	
	public boolean isNovo() {
		return getId() == null;
	}
	
}
