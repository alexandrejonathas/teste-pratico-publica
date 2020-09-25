package br.com.publica.testepratico.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.publica.testepratico.domain.exception.EntidadeEmUsoException;
import br.com.publica.testepratico.domain.exception.JogoNaoEncontradoException;
import br.com.publica.testepratico.domain.repository.PontuacaoRepository;

@Service
public class PontuacaoService {

	@Autowired
	private PontuacaoRepository pontuacaoRep;
	
	
	@Transactional
	public void deletar(Long id) {
		try {			
			pontuacaoRep.deleteById(id);
			pontuacaoRep.flush();
		}catch(EmptyResultDataAccessException e) {
			throw new JogoNaoEncontradoException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Pontuação de código %s não pode ser removida, pois está em uso por outra entidade", id));
		}
	}	
	
}
