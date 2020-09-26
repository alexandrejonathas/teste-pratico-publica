package br.com.publica.testepratico.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


/**
 * Classe que representa um registro na tabela <b>tbl_time</b> e um objeto time no projeto java.
 * @author Jonathas Lima
 * @version 1.0
 * @since Release 01 da aplicação
 */

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tbl_time")
public class Time {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")	
	private Long id;
	
	@NotBlank
	private String nome;
	
	/**
	 * Esse método é responsável por dizer se um objeto é novo ou não.
	 * @return boolean Se é um novo registro ou não
	 */
	public boolean isNovo() {
		return getId() == null;
	}
	
}
