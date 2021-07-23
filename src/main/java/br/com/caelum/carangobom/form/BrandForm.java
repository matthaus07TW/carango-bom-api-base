package br.com.caelum.carangobom.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.caelum.carangobom.model.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandForm {

	@NotBlank
	@Size(min = 2, message = "Deve ter {min} ou mais caracteres.")
	private String name;

	public Brand convert() {
		Brand brand = new Brand(name);
		return brand;
	}
	
	public Brand updateName(Brand brand) {
		brand.setName(name);
		return brand;
	}
}
