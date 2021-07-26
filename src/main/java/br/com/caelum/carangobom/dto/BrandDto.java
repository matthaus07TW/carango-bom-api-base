package br.com.caelum.carangobom.dto;

import br.com.caelum.carangobom.model.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BrandDto {
	
	private Long id;
	private String name;
	
	public BrandDto(Brand brand) {
		id = brand.getId();
		name = brand.getName();
	}
}
