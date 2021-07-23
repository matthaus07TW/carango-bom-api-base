package br.com.caelum.carangobom.dto;

import java.math.BigDecimal;

import br.com.caelum.carangobom.model.Brand;
import br.com.caelum.carangobom.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VehicleDto {

	private Long id;

	private Brand brand;
	
	private String model;
	
	private int year;
	
	private BigDecimal value;
	
	public VehicleDto(Vehicle vehicle) {
		id = vehicle.getId();
		brand = vehicle.getBrand();
		model = vehicle.getModel();
		year = vehicle.getYear();
		value = vehicle.getValue();
	}
	
}
