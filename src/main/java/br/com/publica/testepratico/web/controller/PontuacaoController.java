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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.publica.testepratico.domain.filter.PontuacaoFilter;
import br.com.publica.testepratico.domain.model.Jogo;
import br.com.publica.testepratico.domain.model.Pontuacao;
import br.com.publica.testepratico.domain.model.dto.PontoMes;
import br.com.publica.testepratico.domain.repository.JogoRepository;
import br.com.publica.testepratico.domain.repository.PontuacaoRepository;
import br.com.publica.testepratico.domain.service.PontuacaoService;

@Controller
@RequestMapping("/pontuacoes")
public class PontuacaoController {

	@Autowired
	private PontuacaoRepository pontuacaoRep;
	
	@Autowired
	private PontuacaoService pontuacaoService;

	@Autowired
	private JogoRepository jogoRep;
	
	
	@GetMapping
	public ModelAndView index(PontuacaoFilter filter) {		
		ModelAndView mv = new ModelAndView("/pontuacao/index");
		List<Pontuacao> pontuacoes = pontuacaoRep.filtrar(filter);
		mv.addObject("pontuacoes", pontuacoes);
		return mv;
	}
	
	@GetMapping("/create")
	public ModelAndView create(Pontuacao pontuacao) {
		ModelAndView mv = new ModelAndView("/pontuacao/form");
		List<Jogo> jogos = jogoRep.findAll();
		mv.addObject("jogos", jogos);
		return mv;
	}
	
	@GetMapping("/{id}/edit")
	public ModelAndView edit(@PathVariable("id") Pontuacao pontuacao, RedirectAttributes attributes) {
		
		if(pontuacao == null) {
			attributes.addFlashAttribute("informacao", "Pontuação não encontrada");
			return new ModelAndView("redirect:/pontuacoes");			
		}
		
		ModelAndView mv = create(pontuacao);
		mv.addObject(pontuacao);
		return mv;
	}	
	
	@RequestMapping(value = {"/create", "{\\d+}/edit"}, method = RequestMethod.POST)
	public ModelAndView store(@Valid Pontuacao pontuacao, BindingResult result, Model model, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return create(pontuacao);
		}
		
		pontuacaoRep.save(pontuacao);
		
		attributes.addFlashAttribute("mensagem", "Pontuacao salva com sucesso");
		return new ModelAndView("redirect:/pontuacoes");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		pontuacaoService.deletar(id);
		return ResponseEntity.noContent().build();
	}
	
	@ResponseBody
	@GetMapping("/total-pontos-mes")
	public List<PontoMes> pontosPorMes(){
		return pontuacaoRep.getTotalPontosPorMes();
	}
	
}
