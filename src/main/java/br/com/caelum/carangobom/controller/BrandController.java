package br.com.caelum.carangobom.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import br.com.caelum.carangobom.config.swagger.ApiPageable;
import br.com.caelum.carangobom.form.BrandForm;
import br.com.caelum.carangobom.model.Brand;
import br.com.caelum.carangobom.repository.BrandRepository;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Transactional
@RequestMapping("/brands")
public class BrandController {

	private BrandRepository brandRepository;

	@Autowired
	public BrandController(BrandRepository brandRepository) {
		this.brandRepository = brandRepository;
	}

	@ApiOperation(value = "Find Brands")
	@ApiPageable
	@GetMapping
	public Page<Brand> find(@PageableDefault(sort = "name") @ApiIgnore Pageable pageable) {
		Page<Brand> brands = brandRepository.findAll(pageable);
		return brands;
	}

	@ApiOperation(value = "Find Brand")
	@GetMapping("/{id}")
	public ResponseEntity<Brand> findById(@PathVariable Long id) {
		Optional<Brand> brand = brandRepository.findById(id);
		if (brand.isPresent()) {
			return ResponseEntity.ok(brand.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "Create Brand")
	@PostMapping
	public ResponseEntity<Brand> create(@Valid @RequestBody BrandForm form, UriComponentsBuilder uriBuilder) {
		Brand brand = form.convert(new Brand());
		Brand brandCreated = brandRepository.save(brand);
		URI uri = uriBuilder.path("/brands/{id}").buildAndExpand(brandCreated.getId()).toUri();
		return ResponseEntity.created(uri).body(brandCreated);
	}

	@ApiOperation(value = "Update Brand")
	@PutMapping("/{id}")
	public ResponseEntity<Brand> update(@PathVariable Long id, @Valid @RequestBody BrandForm form) {
		Optional<Brand> brand = brandRepository.findById(id);
		if (brand.isPresent()) {
			Brand brandUpdated = form.convert(brand.get());
			return ResponseEntity.ok(brandUpdated);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "Delete Brand")
	@DeleteMapping("/{id}")
	public ResponseEntity<Brand> delete(@PathVariable Long id) {
		Optional<Brand> brand = brandRepository.findById(id);
		if (brand.isPresent()) {
			brandRepository.delete(brand.get());
			return ResponseEntity.ok(brand.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}