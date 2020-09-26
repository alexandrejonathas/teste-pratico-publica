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
import br.com.publica.testepratico.domain.filter.PontuacaoFilter;
import br.com.publica.testepratico.domain.model.Pontuacao;
import br.com.publica.testepratico.domain.repository.PontuacaoRepository;

@Service
public class PontuacaoService {

	@Autowired
	private PontuacaoRepository pontuacaoRep;
	
	public Pontuacao buscar(Long id) {
		return pontuacaoRep.findById(id).orElse(null);
	}
	
	public List<Pontuacao> filtrar(PontuacaoFilter filter){
		return pontuacaoRep.filtrar(filter);
	}
	
	@Transactional
	public Pontuacao salvar(Pontuacao pontuacao) {
		
		Pontuacao pontuacaoExistente = pontuacaoRep.findByJogo(pontuacao.getJogo());
		
		if(pontuacaoExistente != null && !pontuacaoExistente.getId().equals(pontuacao.getId())) {
			throw new EntidadeExistenteException("Já existe uma pontuação cadastrada para esse jogo");
		}
		
		return pontuacaoRep.save(pontuacao);
	}
	
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
