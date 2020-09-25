package br.com.publica.testepratico.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.publica.testepratico.domain.exception.EntidadeEmUsoException;
import br.com.publica.testepratico.domain.exception.TimeNaoEncontradoException;
import br.com.publica.testepratico.domain.repository.TimeRepository;

@Service
public class TimeService {

	@Autowired
	private TimeRepository timeRep;
	
	
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
