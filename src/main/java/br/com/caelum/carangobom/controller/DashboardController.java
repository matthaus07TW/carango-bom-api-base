package br.com.caelum.carangobom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.caelum.carangobom.interfaces.VehicleDashboard;
import br.com.caelum.carangobom.repository.VehicleRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private VehicleRepository vehicleRepository;
	
	@GetMapping
	@ApiOperation(value = "Find Vehicles by brand")
	public ResponseEntity<List<VehicleDashboard>> find() {
		List<VehicleDashboard> dashboardInfos = vehicleRepository.mapVehicleDashboardByBrand();
		return ResponseEntity.ok(dashboardInfos);
	}

}
