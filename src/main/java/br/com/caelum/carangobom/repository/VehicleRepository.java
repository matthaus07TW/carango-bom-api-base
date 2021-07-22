package br.com.caelum.carangobom.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.caelum.carangobom.interfaces.VehicleDashboard;
import br.com.caelum.carangobom.model.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

	Page<Vehicle> findAll(Specification<Vehicle> spec, Pageable pageable);
	
	@Query(value = "SELECT b.name AS brandName, COUNT(*) AS amount, SUM(v.value) AS total FROM vehicle v, brand b "
			+ "WHERE v.brand_id = b.id GROUP BY b.name", nativeQuery = true)
	List<VehicleDashboard> mapVehicleDashboardByBrand();

}
