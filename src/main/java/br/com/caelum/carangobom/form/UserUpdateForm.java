package br.com.caelum.carangobom.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.caelum.carangobom.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateForm {

	@NotNull
	@NotEmpty
	private String password;

	public User convert() {
		User user = new User();
		user.setPassword(password);
		
		return user;
	}

	public User update(User user) {
		user.setPassword(password);
		return user;
	}

}
