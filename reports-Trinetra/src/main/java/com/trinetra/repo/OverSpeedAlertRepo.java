package com.trinetra.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.OverSpeedAlert;

@Repository
public interface OverSpeedAlertRepo extends JpaRepository<OverSpeedAlert, Integer>{

	@Query(value = "select * from view_over_speed_alerts\n"
			+ "where vltd_manufacturer_name=?1 and vehicle_reg_no=?2 and date_time::::timestamp between cast(?3 as timestamp) and cast(?4 as timestamp) \r\n", nativeQuery = true)
	public Page<OverSpeedAlert> getOverSpeedAlertDetailsByFilter(String vltdName, String vehicleRegNo,
			String startDateTime, String endDateTime, Pageable pageable);
}
