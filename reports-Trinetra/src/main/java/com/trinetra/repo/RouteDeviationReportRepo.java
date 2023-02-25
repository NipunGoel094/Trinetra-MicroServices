package com.trinetra.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.RouteDeviationReport;

@Repository
public interface RouteDeviationReportRepo extends JpaRepository<RouteDeviationReport, Integer>{

	@Query(value = "select * from view_route_deviation_report where route_name = ?1 and vehicle_reg_no = ?2 and start_time between cast(?3 as timestamp) and cast(?4 as timestamp)", nativeQuery = true)
	Page<RouteDeviationReport> getRouteDeviationDetailsByFilter(String routeName, String vehicleRegNo,
			String startDateTime, String endDateTime, Pageable pageable);
}
