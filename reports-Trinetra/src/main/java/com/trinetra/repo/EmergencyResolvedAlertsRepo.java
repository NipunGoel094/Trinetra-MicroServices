package com.trinetra.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.EmergencyResolvedAlerts;

@Repository
public interface EmergencyResolvedAlertsRepo extends JpaRepository<EmergencyResolvedAlerts, Integer>{

	@Query(value = "select * from view_emergency_resolved_alert_report where vltd_manufacturer_name=:vltdName and vehicle_reg_no=:vehicleRegNo and alert_generation_time between cast(:startDate as timestamp) and cast(:endDate as timestamp)\n", nativeQuery = true)
	public Page<EmergencyResolvedAlerts> getEmergencyResolvedAlertReportByFilter(
			@Param("vltdName") String vltdName, @Param("vehicleRegNo") String vehicleRegNo,
			@Param("startDate") String startDate, @Param("endDate") String endDate, Pageable pageable);
}
