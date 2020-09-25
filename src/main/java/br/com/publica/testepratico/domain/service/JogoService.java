package br.com.publica.testepratico.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.publica.testepratico.domain.exception.EntidadeEmUsoException;
import br.com.publica.testepratico.domain.exception.JogoNaoEncontradoException;
import br.com.publica.testepratico.domain.repository.JogoRepository;

@Service
public class JogoService {

	@Autowired
	private JogoRepository jogoRep;
	
	
	@Transactional
	public void deletar(Long id) {
		try {			
			jogoRep.deleteById(id);
			jogoRep.flush();
		}catch(EmptyResultDataAccessException e) {
			throw new JogoNaoEncontradoException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Jogo de código %s não pode ser removido, pois está em uso por outra entidade", id));
		}
	}	
	
}
