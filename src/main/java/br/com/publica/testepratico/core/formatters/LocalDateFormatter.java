package br.com.publica.testepratico.core.formatters;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class LocalDateFormatter implements Formatter<LocalDate> {

	@Override
	public String print(LocalDate localDate, Locale locale) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return dateTimeFormatter.format(localDate);
	}

	@Override
	public LocalDate parse(String text, Locale locale) throws ParseException {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return LocalDate.parse(text, dateTimeFormatter);
	}

}
