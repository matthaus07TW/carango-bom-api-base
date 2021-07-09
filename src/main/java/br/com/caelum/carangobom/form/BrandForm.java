package br.com.caelum.carangobom.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.caelum.carangobom.model.Brand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BrandForm {

	@NotBlank
	@Size(min = 2, message = "Deve ter {min} ou mais caracteres.")
	private String name;

	public Brand convert(Brand brand) {
		brand.setName(name);
		return brand;
	}

	public BrandForm(@NotBlank @Size(min = 2, message = "Deve ter {min} ou mais caracteres.") String name) {
		super();
		this.name = name;
	}
}
