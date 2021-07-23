package br.com.caelum.carangobom.dto;

import java.math.BigDecimal;

import br.com.caelum.carangobom.interfaces.VehicleDashboard;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VehicleDashboardDto implements VehicleDashboard{

	private String brandName;
	
	private int amount;
	
	private BigDecimal total;
	
}
