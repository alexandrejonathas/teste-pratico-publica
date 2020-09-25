package br.com.publica.testepratico.domain.exception;


public class PontuacaoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public PontuacaoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public PontuacaoNaoEncontradaException(Long id) {
		this(String.format("Não existe um cadastro de Pontuação com código %d", id));
	}
	
}
