package br.com.publica.testepratico.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.publica.testepratico.domain.exception.NegocioException;
import br.com.publica.testepratico.domain.model.dto.PeriodoRelatorio;
import br.com.publica.testepratico.domain.service.RelatorioService;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

	@Autowired
	private RelatorioService relatorioService;
	
	@GetMapping("/pontuacao-periodo")
	public ModelAndView relatorioPontuacaoPeriodo(PeriodoRelatorio periodoRelatorio) {
		ModelAndView mv = new ModelAndView("/relatorio/pontuacao-periodo");
		return mv;
	}
	
	@PostMapping("/pontuacao-periodo")
	public ResponseEntity<byte[]> gerarRelatorioPontuacaoPeriodo(PeriodoRelatorio periodoRelatorio, BindingResult bindResult) throws Exception{	
		
		if(bindResult.hasErrors()) {
			throw new NegocioException("As datas são de preenchimento obrigatório!");
		}		
		
		byte[] relatorio = relatorioService.gerarRelatorioPontuacaoPorPeriodo(periodoRelatorio);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
					.body(relatorio);
		
	}
	
	
	
}
