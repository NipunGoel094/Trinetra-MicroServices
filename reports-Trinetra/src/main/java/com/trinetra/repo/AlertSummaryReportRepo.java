package com.trinetra.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.AlertSummaryReport;

@Repository
public interface AlertSummaryReportRepo extends JpaRepository<AlertSummaryReport, Integer>{
	
	@Query(value = "select * from view_alert_summary_report where vltd_manufacturer_name = :vltdName and vehicle_reg_no = :vehicleRegNo and alert_type =:alertName and alert_date_time::::date = cast(:alertDate as date)", nativeQuery = true)
	Page<AlertSummaryReport> getAlertSummaryDetailsByFilter(@Param("vltdName") String vltdName,
			@Param("vehicleRegNo") String vehicleRegNo, @Param("alertName") String alertName,
			@Param("alertDate") String alertDate, Pageable pageable);

}
