package com.trinetra.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.VehicleActivation;

@Repository
public interface VehicleActivationRepo extends JpaRepository<VehicleActivation, Integer>{
	
	
	@Query(value = "select * from vehicle_activation", nativeQuery = true)
	public Page<Object[]> getDeviceList(Pageable pageable);
	
	@Query(value = "select count(*) from vehicle_activation", nativeQuery = true)
	public int getDeviceListCount();

	@Query(value= "select * from vehicle_activation\n"
			+ "where device_imei ilike %:searchBy% or device_sr_no ilike %:searchBy% or device_iccid ilike %:searchBy%\n"
			+ "or vehicle_reg_no ilike %:searchBy% or vehicle_chassis_no ilike %:searchBy% or permit_holder_name ilike %:searchBy% \n"
			+ "or primary_mobile_no ilike %:searchBy% or manufacturer_name ilike %:searchBy% or rto_name ilike %:searchBy% \n"
			+ "or network_provider1 ilike %:searchBy% or dealer_name ilike %:searchBy%", nativeQuery = true)
	Page<Object[]> getDeviceWithSearchList(Pageable pageable, @Param("searchBy") String searchBy);
}
