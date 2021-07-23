package br.com.caelum.carangobom.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.caelum.carangobom.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {

	@NotNull
	@NotEmpty
	private String username;
	@NotNull
	@NotEmpty
	private String password;

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
