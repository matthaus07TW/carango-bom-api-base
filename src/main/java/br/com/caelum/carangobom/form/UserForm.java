package br.com.caelum.carangobom.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.caelum.carangobom.model.User;

public class UserForm {

	@NotNull
	@NotEmpty
	private String username;
	@NotNull
	@NotEmpty
	private String password;

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User convert() {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);

		return user;

	}

	public User update(User user) {
		
		user.setUsername(username);
		user.setPassword(password);

		return user;

	}

}
