package com.trinetra.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.StateERSData;

@Repository
public interface StateERSDataRepo extends JpaRepository<StateERSData, Integer>{
	
	@Query(value = "select * from view_state_ers_data\n"
			+ "where alert_type ilike %:text% or vehicle_chassis_no ilike %:text% or device_imei ilike %:text% or rto_name ilike %:text% or state_name ilike %:text%\n"
			+ "order by alert_time desc", nativeQuery = true)
	public Page<StateERSData> searchStateERSDataByColumns(@Param("text") String text, Pageable pageable);

	@Query(value = "select * from view_state_ers_data\n"
			+ "where alert_date=current_date and state_name=?1\n"
			+ "order by alert_time desc", nativeQuery = true)
	public Page<StateERSData> getStateERSDataByState(String stateName,Pageable pageable);
}
