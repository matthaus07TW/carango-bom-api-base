package br.com.caelum.carangobom.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.caelum.carangobom.form.VehicleForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne
	private Brand brand;
	
	private String model;
	
	private int year;
	
	private BigDecimal value;

	public Vehicle(VehicleForm form) {
		this.model = form.getModel();
		this.year = form.getYear();
		this.value = form.getValue();
	}
}
