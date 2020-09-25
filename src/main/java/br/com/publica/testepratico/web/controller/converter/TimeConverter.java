package br.com.publica.testepratico.web.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.publica.testepratico.domain.model.Time;

@Component
public class TimeConverter implements Converter<String, Time> {

	@Override
	public Time convert(String id) {
		
		if(StringUtils.isEmpty(id)) return null;

		Time time = new Time();
		time.setId(Long.valueOf(id));
		return time;
	}

}
