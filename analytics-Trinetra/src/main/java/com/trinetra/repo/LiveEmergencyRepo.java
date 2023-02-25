package com.trinetra.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.LiveEmergency;

@Repository
public interface LiveEmergencyRepo extends JpaRepository<LiveEmergency, Integer>{

	@Query(value = "select * from view_live_emergency\n"
			+ "where vltd_manufacturer_name ilike %:text% or device_sr_no ilike %:text% or device_imei ilike %:text% or device_iccid ilike %:text% or vehicle_reg_no ilike %:text%\n"
			+ "or vehicle_chassis_no ilike %:text% or permit_holder_name ilike %:text%", nativeQuery = true)
	public Page<LiveEmergency> getLiveEmergencyListByColumns(@Param("text") String text, Pageable pageable);

	@Query(value = "select count(*) from view_live_emergency", nativeQuery = true)
	public Integer getEmergencyAlertCount();
}
