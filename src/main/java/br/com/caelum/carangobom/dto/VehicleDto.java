package br.com.caelum.carangobom.dto;

import java.math.BigDecimal;

import br.com.caelum.carangobom.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VehicleDto {

	private Long id;

	private String brand;
	
	private String model;
	
	private int year;
	
	private BigDecimal value;
	
	public VehicleDto(Vehicle vehicle) {
		this.id = vehicle.getId();
		this.brand = vehicle.getBrand().getName();
		this.model = vehicle.getModel();
		this.year = vehicle.getYear();
		this.value = vehicle.getValue();
	}
	
}