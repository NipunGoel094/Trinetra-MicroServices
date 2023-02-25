package com.trinetra.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.CurrentVehicleRegNo;

@Repository
public interface CurrentVehicleRegNoRepo extends JpaRepository<CurrentVehicleRegNo, Integer>{

	@Query(value="select * from current_vehicle_reg_no where vehicle_reg_no ilike %:vehicleRegNo%",nativeQuery = true)
	public List<CurrentVehicleRegNo> getlistOfCurrentDateVehicleRegNo(@Param("vehicleRegNo")String vehicleRegNo);
}
