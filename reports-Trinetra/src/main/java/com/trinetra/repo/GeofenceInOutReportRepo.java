package com.trinetra.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.GeofenceInOutReport;

@Repository
public interface GeofenceInOutReportRepo extends JpaRepository<GeofenceInOutReport, Integer>{

	@Query(value = "select * from view_geofence_in_out_report where vltd_manufacturer_name=?1 and vehicle_reg_no=?2 and in_time::::date between cast(?3 as date) and cast(?4 as date)", nativeQuery = true)
	Page<GeofenceInOutReport> getGeofenceInOutReportByFilter(String vltdName, String vehicleRegNo,
			String startDate, String endDate, Pageable pageable);
}
