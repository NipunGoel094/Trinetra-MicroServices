package com.trinetra.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.HistoryLogReport;

@Repository
public interface HistoryLogReportRepo extends JpaRepository<HistoryLogReport, Integer>{

	@Query(value="select * from view_history_log_report\n"
			+ "where vehicle_reg_no=:vehicleRegNo and vltd_manufacturer_name=:vltdName\r\n"
			+ "and polling_date_time BETWEEN cast(:startDateTime as timestamp) AND cast(:endDateTime as timestamp)",nativeQuery=true)
	Page<HistoryLogReport> getHistoryLogReport(@Param("vltdName") String vltdName,@Param("vehicleRegNo") String vehicleRegNo,@Param("startDateTime") String startDateTime,@Param("endDateTime") String endDateTime, Pageable pageable);

    @Query(value="select * from view_history_log_report\n"
    		+ "where vltd_manufacturer_name ilike %:input% or dealer_name ilike %:input%\r\n"
    		+ "or permit_holder_name ilike %:input% or rto_name ilike %:input% or device_imei ilike %:input% or vehicle_reg_no ilike %:input% or alert ilike %:input%",nativeQuery = true)
	Page<HistoryLogReport> searchByHistoryLogReport(@Param("input") String input, Pageable pageable);
}
