package com.trinetra.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.DistrictMasterView;

@Repository
public interface DistrictMasterViewRepo extends JpaRepository<DistrictMasterView, Integer>{
	
	@Query(value="select * from district_master_view where state_id =?1 order by district_name asc",nativeQuery = true)
	List<DistrictMasterView> getDistrictListByStateId(int stateId);

	
}
