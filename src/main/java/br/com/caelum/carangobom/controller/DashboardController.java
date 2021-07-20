package br.com.caelum.carangobom.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.caelum.carangobom.interfaces.Dashboard;
import br.com.caelum.carangobom.repository.DashboardRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

	private DashboardRepository dashboardRepository;

	public DashboardController(DashboardRepository dashboardRepository) {
		this.dashboardRepository = dashboardRepository;
	}
	
	@GetMapping
	@ApiOperation(value = "Find Vehicles by brand")
	public ResponseEntity<?> find() {
		List<Dashboard> dashboardInfos = dashboardRepository.mappDashboardInfosByBrand();
		return ResponseEntity.ok(dashboardInfos);
	}

}
