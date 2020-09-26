package br.com.publica.testepratico.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.publica.testepratico.domain.exception.EntidadeEmUsoException;
import br.com.publica.testepratico.domain.exception.EntidadeExistenteException;
import br.com.publica.testepratico.domain.exception.JogoNaoEncontradoException;
import br.com.publica.testepratico.domain.filter.JogoFilter;
import br.com.publica.testepratico.domain.model.Jogo;
import br.com.publica.testepratico.domain.repository.JogoRepository;

@Service
public class JogoService {

	@Autowired
	private JogoRepository jogoRep;
	
	public Jogo buscar(Long id) {
		return jogoRep.findById(id).orElse(null);
	}
	
	public List<Jogo> filtrar(JogoFilter filter){
		return jogoRep.filtrar(filter);
	}	
	
	@Transactional
	public Jogo salvar(Jogo jogo) {
		
		Jogo jogoExistente = jogoRep.findByDataAndTimeCasaAndTimeVisitante(jogo.getData(), jogo.getTimeCasa(), jogo.getTimeVisitante());
		
		if(jogoExistente != null && !jogoExistente.getId().equals(jogo.getId())) {
			throw new EntidadeExistenteException("Já existe um jogo cadastrado com essas informações!");
		}
		
		return jogoRep.save(jogo);
	}
	
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
