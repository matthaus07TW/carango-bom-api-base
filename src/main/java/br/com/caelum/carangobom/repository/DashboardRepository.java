package br.com.caelum.carangobom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.caelum.carangobom.interfaces.Dashboard;
import br.com.caelum.carangobom.model.Vehicle;

@Repository
public interface DashboardRepository extends JpaRepository<Vehicle, Long> {

	@Query(value = "SELECT b.name AS brandName, COUNT(*) AS amount, SUM(v.value) AS total FROM vehicle v, brand b "
			+ "WHERE v.brand_id = b.id GROUP BY b.name", nativeQuery = true)
	List<Dashboard> mappDashboardInfosByBrand();

}
