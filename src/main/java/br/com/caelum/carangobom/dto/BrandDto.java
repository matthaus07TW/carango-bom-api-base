package br.com.caelum.carangobom.dto;

import br.com.caelum.carangobom.model.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BrandDto {
	
	private String name;
	
	public BrandDto(Brand brand) {
		name = brand.getName();
	}
}
