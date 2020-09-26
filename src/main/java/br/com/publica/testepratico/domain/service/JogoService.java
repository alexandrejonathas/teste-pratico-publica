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
import br.com.publica.testepratico.domain.filter.JogoFilter;
import br.com.publica.testepratico.domain.model.Jogo;
import br.com.publica.testepratico.domain.repository.JogoRepository;

/**
 * Classe responsável por implementar as regras de négocios referente a classe jogo.
 * @author Jonathas Lima
 * @version 1.0
 * @since Release 01 da aplicação
 */
@Service
public class JogoService {

	@Autowired
	private JogoRepository jogoRep;
	
	/**
	 * Esse método retorna um jogo pelo id passado.
	 * @param id
	 * @return jogo ou null
	 */	
	public Jogo buscar(Long id) {
		return jogoRep.findById(id).orElse(null);
	}
	
	/**
	 * Esse método retorna uma lista de jogos de acordo com o filtro passado.
	 * @param filter
	 * @return Lista de jogos 
	 */	
	public List<Jogo> filtrar(JogoFilter filter){
		return jogoRep.filtrar(filter);
	}	
	
	/**
	 * Esse método salva um jogo e devolve sua instância, ao tentar cadastrar um jogo existente uma exception de EntidadeExistenteException será lançada.
	 * @param jogo
	 * @return jogo
	 * @throws EntidadeExistenteException Ao tentar cadastrar um jogo existente no banco de dados.
	 * @throws TransactionSystemException Se não atender as validações das proriedades do objeto jogo.
	 */	
	@Transactional
	public Jogo salvar(Jogo jogo) {
		
		Jogo jogoExistente = jogoRep.findByDataAndTimeCasaAndTimeVisitante(jogo.getData(), jogo.getTimeCasa(), jogo.getTimeVisitante());
		
		if(jogoExistente != null && !jogoExistente.getId().equals(jogo.getId())) {
			throw new EntidadeExistenteException("Já existe um jogo cadastrado com essas informações!");
		}
		
		return jogoRep.save(jogo);
	}
	
	/**
	 * Esse método deleta um jogo do banco de dados, ao tentar excluir um registro inexistente lança uma exception de JogoNaoEncontradoException, 
	 * ao tentar excluir um registro que esteja em uso por outra entidade será lançada uma exception de EntidadeEmUsoException.
	 * @param id
	 * @throws JogoNaoEncontradoException Ao tentar excluir um registro inexistente.
	 * @throws EntidadeEmUsoException ao tentar excluir um registro que esteja em uso por outra entidade.
	 */	
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
