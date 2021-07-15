package br.com.caelum.carangobom.form;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.caelum.carangobom.model.Brand;
import br.com.caelum.carangobom.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleForm {
	
	@NotNull
	private Long brandId;

	@NotBlank
	@Size(min = 2, message = "Deve ter {min} ou mais caracteres.")
	private String model;
	
	@NotNull
	private int year;
	
	@NotNull
	@Min(value = 1)
	private BigDecimal value;

	public Vehicle convert(Brand brand) {
		Vehicle vehicle = new Vehicle(this);
		vehicle.setBrand(brand);
		
		return vehicle;
	}

	public Vehicle update(Vehicle vehicle, Brand brand) {
		vehicle.setBrand(brand);
		vehicle.setModel(model);
		vehicle.setYear(year);
		vehicle.setValue(value);
		
		return vehicle;
	}
}
