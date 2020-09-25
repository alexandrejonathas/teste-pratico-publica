package br.com.publica.testepratico.domain.exception;


public class TimeNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public TimeNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public TimeNaoEncontradoException(Long id) {
		this(String.format("Não existe um cadastro de Time com código %d", id));
	}
	
}
