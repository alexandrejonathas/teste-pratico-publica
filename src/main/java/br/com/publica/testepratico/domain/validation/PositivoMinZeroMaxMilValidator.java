package br.com.publica.testepratico.domain.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PositivoMinZeroMaxMilValidator implements ConstraintValidator<PositivoMinZeroAndMax, Integer>  {

	private Integer numeroMaximo;
	
	@Override
	public void initialize(PositivoMinZeroAndMax constraintAnnotation) {
		this.numeroMaximo = constraintAnnotation.max();
	}
	
	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		
		if(value == null) return false;
		
		return value >=0 && value <= this.numeroMaximo;
	}

}
