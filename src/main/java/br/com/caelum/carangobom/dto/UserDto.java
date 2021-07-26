package br.com.caelum.carangobom.dto;

import br.com.caelum.carangobom.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {
	
	private Long id;
	
	private String username;
	
	public UserDto(User user) {
		id = user.getId();
		username = user.getUsername();
	}
	
	
}
