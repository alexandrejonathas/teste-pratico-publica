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

import br.com.publica.testepratico.domain.exception.EntidadeExistenteException;
import br.com.publica.testepratico.domain.filter.TimeFilter;
import br.com.publica.testepratico.domain.model.Time;
import br.com.publica.testepratico.domain.service.TimeService;

@Controller
@RequestMapping(value="/times")
public class TimeController {
	
	@Autowired
	private TimeService timeService;
	
	@GetMapping
	public ModelAndView pesquisar(TimeFilter filter, BindingResult result) {
		ModelAndView mv = new ModelAndView("/time/index");		
		List<Time> times = timeService.filtrar(filter);
		mv.addObject("times", times);
		
		return mv;
	}
	
	@RequestMapping("/create")
	public ModelAndView create(Time time) {
		ModelAndView mv = new ModelAndView("/time/form");
		return mv;
	}
	
	@GetMapping("/{id}/edit")
	public ModelAndView edit(@PathVariable("id") Time time, RedirectAttributes attributes) {
		
		if(time == null) {
			attributes.addFlashAttribute("informacao", "Time não encontrado");
			return new ModelAndView("redirect:/times");			
		}
		
		ModelAndView mv = create(time);
		mv.addObject(time);
		return mv;
	}	
	
	@RequestMapping(value = { "/create", "{\\d+}/edit" }, method = {RequestMethod.POST})
	public ModelAndView store(@Valid Time time,  BindingResult result, Model model, RedirectAttributes attributes) {
		try {
			if(result.hasErrors()) {
				return create(time);
			}
			
			timeService.salvar(time);
			
			attributes.addFlashAttribute("mensagem", "Time salvo com sucesso");
			return new ModelAndView("redirect:/times");
		}catch (EntidadeExistenteException e) {
			ModelAndView mv = create(time);
			mv.addObject(time);
			mv.addObject("informacao", e.getMessage());
			return mv;
		}
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		timeService.deletar(id);
		return ResponseEntity.noContent().build();
	}	
	
	
}
