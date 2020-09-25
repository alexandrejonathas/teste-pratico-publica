package br.com.publica.testepratico.web.controller.handle;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problema {
	
	private Integer status;

	private OffsetDateTime timeStamp;
	
	
	private String type;
	
	
	private String title;
	
	
	private String detail;

	
	private String message;
	
	
	private List<Object> objects;

	
	@Getter
	@Builder
	public static class Object {
		
		private String name;
		
		private String userMessage;
	}
	
}
