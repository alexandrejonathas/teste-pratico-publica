package br.com.publica.testepratico.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.publica.testepratico.domain.exception.EntidadeEmUsoException;
import br.com.publica.testepratico.domain.exception.EntidadeExistenteException;
import br.com.publica.testepratico.domain.exception.TimeNaoEncontradoException;
import br.com.publica.testepratico.domain.filter.TimeFilter;
import br.com.publica.testepratico.domain.model.Time;
import br.com.publica.testepratico.domain.repository.TimeRepository;


/**
 * Classe responsável por implementar as regras de négocios referente a classe time.
 * @author Jonathas Lima
 * @version 1.0
 * @since Release 01 da aplicação
 */

@Service
public class TimeService {

	@Autowired
	private TimeRepository timeRep;
	
	
	/**
	 * Esse método retorna um time pelo id passado.
	 * @param id
	 * @return time ou null
	 */
	public Time buscar(Long id) {
		return timeRep.findById(id).orElse(null);
	}
	
	/**
	 * Esse método retorna uma lista de times de acordo com o filtro passado.
	 * @param filter
	 * @return Lista de times 
	 */
	public List<Time> filtrar(TimeFilter filter){
		return timeRep.filtrar(filter);
	}
	
	/**
	 * Esse método salva um time e devolve sua instância, ao tentar cadastrar um time existente uma exception de EntidadeExistenteException será lançada.
	 * @param time
	 * @return time
	 * @throws EntidadeExistenteException Ao tentar cadastrar um time com um nome existente no banco de dados.
	 * @throws TransactionSystemException Se não atender as validações das proriedades do objeto time.
	 */
	@Transactional
	public Time salvar(Time time) {
		
		Time timeExistente = timeRep.findByNomeIgnoreCase(time.getNome());
		
		if(timeExistente != null && !timeExistente.getId().equals(time.getId())) {
			throw new EntidadeExistenteException("Já existe um time com esse nome");
		}
		
		return timeRep.save(time);
	}
	
	
	/**
	 * Esse método deleta um time do banco de dados, ao tentar excluir um registro inexistente lança uma exception de TimeNaoEncontradoException, 
	 * ao tentar excluir um registro que esteja em uso por outra entidade será lançada uma exception de EntidadeEmUsoException.
	 * @param id
	 * @throws TimeNaoEncontradoException Ao tentar excluir um registro inexistente.
	 * @throws EntidadeEmUsoException ao tentar excluir um registro que esteja em uso por outra entidade.
	 */
	@Transactional
	public void deletar(Long id) {
		try {			
			timeRep.deleteById(id);
			timeRep.flush();
		}catch(EmptyResultDataAccessException e) {
			throw new TimeNaoEncontradoException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Time de código %s não pode ser removido, pois está em uso por outra entidade", id));
		}
	}	
	
}
