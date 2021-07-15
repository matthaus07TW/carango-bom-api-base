package br.com.caelum.carangobom.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.caelum.carangobom.config.swagger.VehicleFilterPageable;
import br.com.caelum.carangobom.dto.VehicleDto;
import br.com.caelum.carangobom.form.VehicleForm;
import br.com.caelum.carangobom.model.Brand;
import br.com.caelum.carangobom.model.Vehicle;
import br.com.caelum.carangobom.repository.BrandRepository;
import br.com.caelum.carangobom.repository.VehicleRepository;
import io.swagger.annotations.ApiOperation;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Transactional
@RequestMapping("/vehicles")
public class VehicleController {

	private VehicleRepository vehicleRepository;
	private BrandRepository brandRepository;

	@Autowired
	public VehicleController(VehicleRepository vehicleRepository, BrandRepository brandRepository) {
		this.vehicleRepository = vehicleRepository;
		this.brandRepository = brandRepository;
	}

	@ApiOperation(value = "Find Vehicles")
	@VehicleFilterPageable
	@GetMapping
	public Page<VehicleDto> find(
			@And({ @Spec(path = "brand.name", params = "brandName", spec = Like.class),
					@Spec(path = "model", spec = Like.class)
			}) Specification<Vehicle> spec,
			@PageableDefault(sort = "model") @ApiIgnore Pageable pageable) {
		Page<Vehicle> vehiclesPage;
		
		if (spec != null) {
			vehiclesPage = vehicleRepository.findAll(spec, pageable);
		} else {
			vehiclesPage = vehicleRepository.findAll(pageable);
		}
		
		Page<VehicleDto> vehicleDtoPage = vehiclesPage.map(VehicleDto::new);
		return vehicleDtoPage;
	}

	@ApiOperation(value = "Find Vehicle")
	@GetMapping("/{id}")
	public ResponseEntity<VehicleDto> findById(@PathVariable Long id) {
		Optional<Vehicle> vehicle = vehicleRepository.findById(id);
		if (vehicle.isPresent()) {
			return ResponseEntity.ok(new VehicleDto(vehicle.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@ApiOperation(value = "Create Vehicle")
	@PostMapping
	public ResponseEntity<VehicleDto> create(@Valid @RequestBody VehicleForm form, UriComponentsBuilder uriBuilder) {
		Optional<Brand> brand = brandRepository.findById(form.getBrandId());
		if (brand.isPresent()) {
			Vehicle vehicle = form.convert(brand.get());
			Vehicle vehicleCreated = vehicleRepository.save(vehicle);
			URI uri = uriBuilder.path("/vehicles/{id}").buildAndExpand(vehicleCreated.getId()).toUri();
			return ResponseEntity.created(uri).body(new VehicleDto(vehicleCreated));
		}

		return ResponseEntity.badRequest().build();
	}

	@ApiOperation(value = "Update Vehicle")
	@PutMapping("/{id}")
	public ResponseEntity<VehicleDto> update(@PathVariable Long id, @Valid @RequestBody VehicleForm form) {
		Optional<Vehicle> vehicle = vehicleRepository.findById(id);
		if (vehicle.isPresent()) {
			Optional<Brand> brand = brandRepository.findById(form.getBrandId());
			if (brand.isPresent()) {
				Vehicle vehicleUpdated = form.update(vehicle.get(), brand.get());
				return ResponseEntity.ok(new VehicleDto(vehicleUpdated));

			}

			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.notFound().build();
	}

	@ApiOperation(value = "Delete Vehicle")
	@DeleteMapping("/{id}")
	public ResponseEntity<VehicleDto> delete(@PathVariable Long id) {
		Optional<Vehicle> vehicle = vehicleRepository.findById(id);
		if (vehicle.isPresent()) {
			vehicleRepository.delete(vehicle.get());
			return ResponseEntity.ok(new VehicleDto(vehicle.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}