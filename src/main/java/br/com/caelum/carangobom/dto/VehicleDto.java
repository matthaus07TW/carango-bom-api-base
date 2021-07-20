package br.com.caelum.carangobom.dto;

import java.math.BigDecimal;

import br.com.caelum.carangobom.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VehicleDto {

	private Long id;

	private Long brandId;
	
	private String model;
	
	private int year;
	
	private BigDecimal value;
	
	public VehicleDto(Vehicle vehicle) {
		this.id = vehicle.getId();
		this.brandId = vehicle.getBrand().getId();
		this.model = vehicle.getModel();
		this.year = vehicle.getYear();
		this.value = vehicle.getValue();
	}
	
}
