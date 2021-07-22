package br.com.caelum.carangobom.dto;

import br.com.caelum.carangobom.model.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDto {
	
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	
	public UserDto(User user) {
		username = user.getUsername();
		password = user.getPassword();
	}
	
	
}
