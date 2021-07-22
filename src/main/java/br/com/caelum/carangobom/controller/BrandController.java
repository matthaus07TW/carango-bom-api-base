package br.com.caelum.carangobom.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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

import br.com.caelum.carangobom.config.swagger.BrandFilterPageable;
import br.com.caelum.carangobom.dto.BrandDto;
import br.com.caelum.carangobom.form.BrandForm;
import br.com.caelum.carangobom.model.Brand;
import br.com.caelum.carangobom.repository.BrandRepository;
import io.swagger.annotations.ApiOperation;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
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
	@BrandFilterPageable
	@GetMapping
	@Cacheable(value = "brandList")
	public Page<BrandDto> find(@Spec(path = "name", spec = Like.class) Specification<Brand> spec,
			@PageableDefault(sort = "name", direction = Direction.ASC, page = 0, size = 10) @ApiIgnore Pageable pagination) {

		Page<Brand> brandsPage;

		if (spec != null) {
			brandsPage = brandRepository.findAll(spec, pagination);
		} else {
			brandsPage = brandRepository.findAll(pagination);
		}

		return brandsPage.map(BrandDto::new);
	}

	@ApiOperation(value = "Find Brand")
	@GetMapping("/{id}")
	@Cacheable(value = "brandList")
	public ResponseEntity<Brand> findById(@PathVariable Long id) {
		Optional<Brand> brand = brandRepository.findById(id);
		if (brand.isPresent()) {
			return ResponseEntity.ok(brand.get());
		}

		return ResponseEntity.notFound().build();
	}

	@ApiOperation(value = "Create Brand")
	@PostMapping
	@CacheEvict(value = "brandList", allEntries = true)
	public ResponseEntity<Brand> create(@Valid @RequestBody BrandForm form, UriComponentsBuilder uriBuilder) {
		Brand brand = form.convert();
		Brand brandCreated = brandRepository.save(brand);
		URI uri = uriBuilder.path("/brands/{id}").buildAndExpand(brandCreated.getId()).toUri();
		return ResponseEntity.created(uri).body(brandCreated);
	}

	@ApiOperation(value = "Update Brand")
	@PutMapping("/{id}")
	@CacheEvict(value = "brandList", allEntries = true)
	public ResponseEntity<Brand> update(@PathVariable Long id, @Valid @RequestBody BrandForm form) {
		Optional<Brand> brand = brandRepository.findById(id);
		if (brand.isPresent()) {
			Brand brandUpdated = form.updateName(brand.get());
			return ResponseEntity.ok(brandUpdated);
		}

		return ResponseEntity.notFound().build();
	}

	@ApiOperation(value = "Delete Brand")
	@DeleteMapping("/{id}")
	@CacheEvict(value = "brandList", allEntries = true)
	public ResponseEntity<Brand> delete(@PathVariable Long id) {
		Optional<Brand> brand = brandRepository.findById(id);
		if (brand.isPresent()) {
			brandRepository.delete(brand.get());
			return ResponseEntity.ok(brand.get());
		}

		return ResponseEntity.notFound().build();
	}

}