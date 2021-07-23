package br.com.caelum.carangobom.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Data;

@Data
public class LoginForm {
	
	@NotNull @NotEmpty
	private String username;
	@NotNull @NotEmpty
	private String password;

	public UsernamePasswordAuthenticationToken convert() {
		return new UsernamePasswordAuthenticationToken(username, password);
	}

}