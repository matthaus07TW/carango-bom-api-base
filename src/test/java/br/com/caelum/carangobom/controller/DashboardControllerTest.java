package br.com.caelum.carangobom.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import br.com.caelum.carangobom.dto.VehicleDashboardDto;
import br.com.caelum.carangobom.interfaces.VehicleDashboard;
import br.com.caelum.carangobom.repository.VehicleRepository;

class DashboardControllerTest {
	
	private DashboardController dashboardController;

	@Mock
	private VehicleRepository vehicleRepository;

	@BeforeEach
	public void configuraMock() {
		openMocks(this);

		dashboardController = new DashboardController(vehicleRepository);
	}

	@Test
	void shouldReturnListIfExists() {
		VehicleDashboardDto vehicleDashboardDto = new VehicleDashboardDto("HYUNDAI", 3, BigDecimal.TEN);
		
		when(vehicleRepository.mapVehicleDashboardByBrand()).thenReturn(Arrays.asList(vehicleDashboardDto));
		
		List<VehicleDashboard> vehicleDashboardResponse = dashboardController.find();
		assertEquals(Arrays.asList(vehicleDashboardDto), vehicleDashboardResponse);
	}

}
