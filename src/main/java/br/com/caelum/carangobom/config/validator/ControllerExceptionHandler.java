package br.com.caelum.carangobom.config.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErrorDto> handle(MethodArgumentNotValidException exception) {
		List<ErrorDto> errors = new ArrayList<>();
		
		exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
			String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			errors.add(new ErrorDto(fieldError.getField(), message));
		});
		
		return errors;
	}
	
}
