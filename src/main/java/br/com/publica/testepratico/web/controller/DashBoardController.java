package br.com.publica.testepratico.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.publica.testepratico.domain.model.dto.RecordeQuebrado;
import br.com.publica.testepratico.domain.repository.PontuacaoRepository;

@Controller
public class DashBoardController {
	
	@Autowired
	private PontuacaoRepository pontuacaoRep;
	
	@GetMapping
	public ModelAndView index(Model model) {
		ModelAndView mv = new ModelAndView("dashboard");
		
		mv.addObject("minimoTemporada", pontuacaoRep.getMinimoTemporada());
		mv.addObject("maximoTemporada", pontuacaoRep.getMaximoTemporada());
		
		RecordeQuebrado rq = pontuacaoRep.getQuantidadeRecordeQuebrados();
		mv.addObject("quantidadeRecordeQuebrado", (rq.getMinimo()+rq.getMaximo()));
		
		return mv;
	}
	
}
