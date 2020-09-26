package br.com.publica.testepratico.domain.exception;

public class EntidadeExistenteException extends NegocioException {

	private static final long serialVersionUID = 1L;

	public EntidadeExistenteException(String mensagem) {
		super(mensagem);
	}
	
}
