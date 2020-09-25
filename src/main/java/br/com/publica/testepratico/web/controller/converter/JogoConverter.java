package br.com.publica.testepratico.web.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.publica.testepratico.domain.model.Jogo;

@Component
public class JogoConverter implements Converter<String, Jogo> {

	@Override
	public Jogo convert(String id) {
		
		if(StringUtils.isEmpty(id)) return null;

		Jogo jogo = new Jogo();
		jogo.setId(Long.valueOf(id));
		return jogo;
	}

}
