package br.com.publica.testepratico.domain.exception;


public class JogoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public JogoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public JogoNaoEncontradoException(Long id) {
		this(String.format("Não existe um cadastro de Jogo com código %d", id));
	}
	
}
