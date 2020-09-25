package br.com.publica.testepratico.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.publica.testepratico.domain.filter.JogoFilter;
import br.com.publica.testepratico.domain.model.Jogo;
import br.com.publica.testepratico.domain.model.Time;
import br.com.publica.testepratico.domain.repository.JogoRepository;
import br.com.publica.testepratico.domain.repository.TimeRepository;
import br.com.publica.testepratico.domain.service.JogoService;

@Controller
@RequestMapping("/jogos")
public class JogoController {

	@Autowired
	private JogoRepository jogoRep;
	
	@Autowired
	private JogoService jogoService;

	@Autowired
	private TimeRepository timeRep;
	
	
	@GetMapping
	public ModelAndView index(JogoFilter filter) {		
		ModelAndView mv = new ModelAndView("/jogo/index");
		List<Jogo> jogos = jogoRep.filtrar(filter);
		mv.addObject("jogos", jogos);
		return mv;
	}
	
	@GetMapping("/create")
	public ModelAndView create(Jogo jogo) {
		ModelAndView mv = new ModelAndView("/jogo/form");
		List<Time> times = timeRep.findAll();
		mv.addObject("times", times);
		return mv;
	}
	
	@GetMapping("/{id}/edit")
	public ModelAndView edit(@PathVariable("id") Jogo jogo, RedirectAttributes attributes) {
		
		if(jogo == null) {
			attributes.addFlashAttribute("informacao", "Jogo não encontrado");
			return new ModelAndView("redirect:/jogos");			
		}
		
		ModelAndView mv = create(jogo);
		mv.addObject(jogo);
		return mv;
	}	
	
	@RequestMapping(value = {"/create", "{\\d+}/edit"}, method = RequestMethod.POST)
	public ModelAndView store(@Valid Jogo jogo, BindingResult result, Model model, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return create(jogo);
		}
		
		jogoRep.save(jogo);
		
		attributes.addFlashAttribute("mensagem", "Jogo salvo com sucesso");
		return new ModelAndView("redirect:/jogos");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		jogoService.deletar(id);
		return ResponseEntity.noContent().build();
	}	
	
}
