package br.com.caelum.carangobom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.caelum.carangobom.model.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

}
