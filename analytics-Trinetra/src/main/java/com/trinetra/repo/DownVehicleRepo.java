package com.trinetra.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.DownVehicle;

@Repository
public interface DownVehicleRepo extends JpaRepository<DownVehicle, Integer>{

	@Query(value = "select * from view_down_vehicle where rto_id = :id", nativeQuery = true)
	public Page<DownVehicle> getDownVehicleListByRtoId(@Param("id") int id, Pageable pageable);
	
	@Query(value = "select * from view_down_vehicle where person_id = :id", nativeQuery = true)
	public Page<DownVehicle> getDownVehicleListByPermitHolderId(@Param("id") int id, Pageable pageable);
	
	@Query(value = "select * from view_down_vehicle where oem_id = :id", nativeQuery = true)
	public Page<DownVehicle> getDownVehicleListByVltdId(@Param("id") int id, Pageable pageable);
	
	@Query(value = "select * from view_down_vehicle\n"
			+ "where vltd_manufacturer_name ilike %:text% or vehicle_reg_no ilike %:text%\n"
			+ "or vehicle_chassis_no ilike %:text% or device_sr_no ilike %:text% or device_imei ilike %:text% or device_iccid ilike %:text%\n"
			+ "or permit_holder_name ilike %:text% or rto_name ilike %:text%", nativeQuery = true)
	public Page<DownVehicle> getSearchResults(@Param("text") String text, Pageable pageable);
	
	@Query(value = "select * from view_down_vehicle\n"
			+ "where vltd_manufacturer_name = :vltdManufacturerName and last_polling_time::::date <current_date and last_polling_time >= now() - :days * interval '1 day'", nativeQuery = true)
	public Page<DownVehicle> getDownvehicleByVltdByDays(@Param("days") int days, @Param("vltdManufacturerName") String vltdManufacturerName, Pageable pageable);

}
