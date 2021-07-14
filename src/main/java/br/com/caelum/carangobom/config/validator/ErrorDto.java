package br.com.caelum.carangobom.config.validator;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {

	private String field;
	private String message;

}
