package br.com.integrator.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class ValidaCartao implements Validator{
	public void validate(FacesContext context, 
			UIComponent componente, Object objeto) 
	throws ValidatorException 
{
	 String cartaoDigitado = (String)objeto; 

     Pattern p = 
				Pattern.compile("((\\d{16}|\\d{4}((-|\\s)\\d{4}){3}))"); 

      Matcher m = p.matcher(cartaoDigitado); 

      boolean matchFound = m.matches( ); 

      if (!matchFound) { 
            FacesMessage message = new FacesMessage( );

            message.setDetail("Número inválido.");
            message.setSummary("Número inválido.");

            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
      }
	}

}
