package br.com.publica.testepratico.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import br.com.publica.testepratico.domain.exception.EntidadeEmUsoException;
import br.com.publica.testepratico.domain.exception.EntidadeExistenteException;
import br.com.publica.testepratico.domain.exception.JogoNaoEncontradoException;
import br.com.publica.testepratico.domain.filter.PontuacaoFilter;
import br.com.publica.testepratico.domain.model.Pontuacao;
import br.com.publica.testepratico.domain.repository.PontuacaoRepository;


/**
 * Classe responsável por implementar as regras de négocios referente a classe pontuacao.
 * @author Jonathas Lima
 * @version 1.0
 * @since Release 01 da aplicação
 */
@Service
public class PontuacaoService {

	@Autowired
	private PontuacaoRepository pontuacaoRep;
	
	/**
	 * Esse método retorna uma pontuacao pelo id passado.
	 * @param id
	 * @return pontuacao ou null
	 */		
	public Pontuacao buscar(Long id) {
		return pontuacaoRep.findById(id).orElse(null);
	}
	
	/**
	 * Esse método retorna uma lista de pontuacoes de acordo com o filtro passado.
	 * @param filter
	 * @return Lista de pontuacoes 
	 */		
	public List<Pontuacao> filtrar(PontuacaoFilter filter){
		return pontuacaoRep.filtrar(filter);
	}
	
	/**
	 * Esse método salva uma pontuacao e devolve sua instância, ao tentar cadastrar uma pontuacao existente uma exception de EntidadeExistenteException será lançada.
	 * @param pontuacao
	 * @return pontuacao
	 * @throws EntidadeExistenteException Ao tentar cadastrar uma pontuacao existente no banco de dados.
	 * @throws TransactionSystemException Se não atender as validações das proriedades do objeto pontuacao.
	 */		
	@Transactional
	public Pontuacao salvar(Pontuacao pontuacao) {
		
		Pontuacao pontuacaoExistente = pontuacaoRep.findByJogo(pontuacao.getJogo());
		
		if(pontuacaoExistente != null && !pontuacaoExistente.getId().equals(pontuacao.getId())) {
			throw new EntidadeExistenteException("Já existe uma pontuação cadastrada para esse jogo");
		}
		
		return pontuacaoRep.save(pontuacao);
	}
	
	/**
	 * Esse método deleta uma pontuacao do banco de dados, ao tentar excluir um registro inexistente lança uma exception de PontuacaoNaoEncontradoException, 
	 * ao tentar excluir um registro que esteja em uso por outra entidade será lançada uma exception de EntidadeEmUsoException.
	 * @param id
	 * @throws PontuacaoNaoEncontradoException Ao tentar excluir um registro inexistente.
	 * @throws EntidadeEmUsoException ao tentar excluir um registro que esteja em uso por outra entidade.
	 */		
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
