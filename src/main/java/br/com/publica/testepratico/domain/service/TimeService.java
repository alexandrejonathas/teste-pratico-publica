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

@Service
public class TimeService {

	@Autowired
	private TimeRepository timeRep;
	
	public Time buscar(Long id) {
		return timeRep.findById(id).orElse(null);
	}
	
	public List<Time> filtrar(TimeFilter filter){
		return timeRep.filtrar(filter);
	}
	
	@Transactional
	public Time salvar(Time time) {
		
		Time timeExistente = timeRep.findByNomeIgnoreCase(time.getNome());
		
		if(timeExistente != null && !timeExistente.getId().equals(time.getId())) {
			throw new EntidadeExistenteException("Já existe um time com esse nome");
		}
		
		return timeRep.save(time);
	}
	
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
