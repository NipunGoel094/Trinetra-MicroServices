package com.trinetra.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.EmergencyAlertsReport;

@Repository
public interface EmergencyAlertsReportRepo extends JpaRepository<EmergencyAlertsReport, Integer>{
	
	@Query(value = "select * from view_emergency_alert where vltd_manufacturer_name=?1 and vehicle_reg_no=?2 and alert_date_time::::character varying between ?3 and ?4", nativeQuery = true)
	Page<EmergencyAlertsReport> getEmergencyAlertsDetailsByFilter(String vltdName, String vehicleRegNo, String startDateTime,
			String endDateTime,Pageable pageable);
}
