package br.com.publica.testepratico.web.controller.handle;

import java.time.OffsetDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.publica.testepratico.domain.exception.EntidadeEmUsoException;
import br.com.publica.testepratico.domain.exception.EntidadeNaoEncontradaException;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex,
			WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemaTipo problemaTipo = ProblemaTipo.ENTIDADE_EM_USO;
		String detalhe = ex.getMessage();
		Problema problema = criarProblemaBuilder(status, problemaTipo, detalhe).message(detalhe).build();
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}	
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> tratarEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		ProblemaTipo problemaTipo = ProblemaTipo.ENTIDADE_EM_USO;
		String detalhe = ex.getMessage();
		Problema problema = criarProblemaBuilder(status, problemaTipo, detalhe).message(detalhe).build();
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}	
	
	private Problema.ProblemaBuilder criarProblemaBuilder(HttpStatus status, ProblemaTipo problemaTipo, String detalhe) {
		return Problema.builder().status(status.value()).title(problemaTipo.getTitulo())
				.detail(detalhe).timeStamp(OffsetDateTime.now());
	}	
	
}
