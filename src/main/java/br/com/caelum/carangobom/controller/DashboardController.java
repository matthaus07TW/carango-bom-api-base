package br.com.caelum.carangobom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.caelum.carangobom.interfaces.VehicleDashboard;
import br.com.caelum.carangobom.repository.VehicleRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

	private VehicleRepository vehicleRepository;
	
	@Autowired
	public DashboardController(VehicleRepository vehicleRepository) {
		this.vehicleRepository = vehicleRepository;
	}
	
	@GetMapping
	@ApiOperation(value = "Find Vehicles by brand")
	public List<VehicleDashboard> find() {
		return vehicleRepository.mapVehicleDashboardByBrand();
	}

}
