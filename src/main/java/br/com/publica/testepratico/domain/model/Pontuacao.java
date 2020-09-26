package br.com.publica.testepratico.domain.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import br.com.publica.testepratico.domain.validation.PositivoMinZeroAndMax;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa um registro na tabela <b>tbl_pontuacao</b> e um objeto pontuacao no projeto java.
 * @author Jonathas Lima
 * @version 1.0
 * @since Release 01 da aplicação
 */

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tbl_pontuacao")
public class Pontuacao {
	
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")	
	private Long id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="jogo_id")
	private Jogo jogo;
	
	@PositivoMinZeroAndMax(max = 1000)
	private Integer placar;

	@PositivoMinZeroAndMax(max = 1000)
	@Column(name = "minimo_temporada")
	private Integer minimoTemporada;

	@PositivoMinZeroAndMax(max = 1000)
	@Column(name = "maximo_temporada")	
	private Integer maximoTemporada;

	@PositivoMinZeroAndMax(max = 1000)
	@Column(name = "quebra_recorde_minimo")	
	private Integer quebraRecordeMinimo;
	
	@PositivoMinZeroAndMax(max = 1000)
	@Column(name = "quebra_recorde_maximo")
	private Integer quebraRecordeMaximo;
	
	/**
	 * Esse método é responsável por dizer se um objeto é novo ou não.
	 * @return boolean Se é um novo registro ou não
	 */	
	public boolean isNovo() {
		return getId() == null;
	}
	
}
