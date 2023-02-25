package com.trinetra.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.LiveVehicle;

@Repository
public interface LiveVehicleRepo extends JpaRepository<LiveVehicle, Integer>{

	@Query(value="select * from view_live_vehicle where vehicle_reg_no=?1",nativeQuery = true)
	public LiveVehicle getVehiclePlotResponse(@Param("vehicleRegNo")String vehicleRegNo);
}
