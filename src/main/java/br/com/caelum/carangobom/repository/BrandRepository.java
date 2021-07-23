package br.com.caelum.carangobom.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.caelum.carangobom.model.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

	Optional<Brand> findByName(String username);

	Page<Brand> findAll(Specification<Brand> spec, Pageable pageable);
}
