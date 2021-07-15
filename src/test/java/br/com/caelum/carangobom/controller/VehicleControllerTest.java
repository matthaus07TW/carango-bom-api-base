package br.com.caelum.carangobom.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.caelum.carangobom.dto.VehicleDto;
import br.com.caelum.carangobom.form.VehicleForm;
import br.com.caelum.carangobom.model.Brand;
import br.com.caelum.carangobom.model.Vehicle;
import br.com.caelum.carangobom.repository.BrandRepository;
import br.com.caelum.carangobom.repository.VehicleRepository;

class VehicleControllerTest {

	private VehicleController vehicleController;
	private UriComponentsBuilder uriBuilder;

	@Mock
	private VehicleRepository vehicleRepository;

	@Mock
	private BrandRepository brandRepository;

	@BeforeEach
	public void configuraMock() {
		openMocks(this);

		vehicleController = new VehicleController(vehicleRepository, brandRepository);
		uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");
	}

	@Test
	void shouldReturnVehicleList() {
		ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

		VehicleDto vehicleTestDto = new VehicleDto(1L, "HYUNDAI", "HB20", 2021, BigDecimal.TEN);
		List<VehicleDto> vehiclesDto = Arrays.asList(vehicleTestDto);
		
		Brand brandTest = new Brand(1L, "HYUNDAI");
		Vehicle vehicleTest = new Vehicle(1L, brandTest, "HB20", 2021, BigDecimal.TEN);
		List<Vehicle> vehicles = Arrays.asList(vehicleTest);

		when(vehicleRepository.findAll(pageableCaptor.capture())).thenReturn(new PageImpl<Vehicle>(vehicles));
		
		Page<VehicleDto> result = vehicleController.find(null, pageableCaptor.capture());
		assertEquals(vehiclesDto, result.getContent());
	}
	
	@Test
	void shouldReturnVehicleById() {
		Brand brandTest = new Brand(1L, "HYUNDAI");
		Vehicle vehicleTest = new Vehicle(1L, brandTest, "HB20", 2021, BigDecimal.TEN);
		VehicleDto vehicleTestDto = new VehicleDto(1L, "HYUNDAI", "HB20", 2021, BigDecimal.TEN);

		when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicleTest));

		ResponseEntity<VehicleDto> response = vehicleController.findById(1L);
		assertEquals(vehicleTestDto, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	void sholdReturnNotFoundError() {
		when(vehicleRepository.findById(anyLong())).thenReturn(Optional.empty());

		ResponseEntity<VehicleDto> response = vehicleController.findById(1L);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	void shouldReturnCreatedAndLocation() {
		Brand brandTest = new Brand(1L, "HYUNDAI");
		when(brandRepository.findById(1L)).thenReturn(Optional.of(brandTest));
		
		when(vehicleRepository.save(any())).then(invocation -> {
			Vehicle vehicleCreated = invocation.getArgument(0, Vehicle.class);
			vehicleCreated.setId(1L);

			return vehicleCreated;
		});
		
		VehicleForm vehicleFormTest = new VehicleForm(1L, "HB20", 2021, BigDecimal.TEN);

		ResponseEntity<VehicleDto> response = vehicleController.create(vehicleFormTest, uriBuilder);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("http://localhost:8080/vehicles/1", response.getHeaders().getLocation().toString());
	}
	
	@Test
	void shouldReturnBadRequestErrorWhenBrandIdIsInvalidCreate() {
		VehicleForm vehicleFormTest = new VehicleForm(1L, "HB20", 2021, BigDecimal.TEN);

		ResponseEntity<VehicleDto> response = vehicleController.create(vehicleFormTest, uriBuilder);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	void shouldUpdateVehicleIfExists() {
		Brand brandTest = new Brand(1L, "HYUNDAI");
		Vehicle vehicleTest = new Vehicle(1L, brandTest, "HB20", 2021, BigDecimal.TEN);
		VehicleDto vehicleTestDto = new VehicleDto(1L, "HYUNDAI", "HB20 (updated)", 2021, BigDecimal.TEN);
		VehicleForm vehicleFormTest = new VehicleForm(1L, "HB20 (updated)", 2021, BigDecimal.TEN);

		when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicleTest));
		when(brandRepository.findById(1L)).thenReturn(Optional.of(brandTest));

		ResponseEntity<VehicleDto> response = vehicleController.update(1L, vehicleFormTest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(vehicleTestDto, response.getBody());
	}
	
	@Test
	void shouldReturnNotFoundWhenVehicleIdIsInvalidUpdate() {
		VehicleForm vehicleFormTest = new VehicleForm(1L, "HB20", 2021, BigDecimal.TEN);

		ResponseEntity<VehicleDto> response = vehicleController.update(1L, vehicleFormTest);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	void shouldReturnBadRequestErrorWhenBrandIdIsInvalidUpdate() {
		Brand brandTest = new Brand(1L, "HYUNDAI");
		Vehicle vehicleTest = new Vehicle(1L, brandTest, "HB20", 2021, BigDecimal.TEN);
		VehicleForm vehicleFormTest = new VehicleForm(1L, "HB20 (updated)", 2021, BigDecimal.TEN);

		when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicleTest));
		
		ResponseEntity<VehicleDto> response = vehicleController.update(1L, vehicleFormTest);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	void shouldDeleteVehicle() {
		Brand brandTest = new Brand(1L, "HYUNDAI");
		Vehicle vehicleTest = new Vehicle(1L, brandTest, "HB20", 2021, BigDecimal.TEN);

		when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicleTest));
		
		ResponseEntity<VehicleDto> response = vehicleController.delete(1L);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(vehicleRepository).delete(vehicleTest);
	}
	
	@Test
	void shouldReturnNotFoundErrorWhenBrandIdIsInvalidDelete() {
		when(vehicleRepository.findById(anyLong())).thenReturn(Optional.empty());
		
		ResponseEntity<VehicleDto> response = vehicleController.delete(1L);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		verify(vehicleRepository, never()).delete(any());
	}

}
