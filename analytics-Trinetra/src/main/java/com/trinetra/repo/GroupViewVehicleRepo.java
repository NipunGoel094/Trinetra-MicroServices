package com.trinetra.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.GroupViewVehicle;

@Repository
public interface GroupViewVehicleRepo extends JpaRepository<GroupViewVehicle, Integer>{

	@Query(value="select * from group_view_vehicle where vehicle_reg_no is not null and lat>0 and lon>0 and state_id=?1 and rto_location_id=?2", nativeQuery = true)
	List<GroupViewVehicle> getDetailsByStateIdAndRto(int stateId, int rtoId);
}
