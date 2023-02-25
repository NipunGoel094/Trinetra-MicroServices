package com.trinetra.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.StateMasterView;

@Repository
public interface DropDownControllerRepo extends JpaRepository<StateMasterView,Integer>{

	@Query(value="select * from state_master_view",nativeQuery = true) 
	public List<StateMasterView> getStateFromStateMaster();
	
	@Query(value="select * from state_master_view where id::::character varying = ?1",nativeQuery = true) 
	public List<StateMasterView> getStateFromStateMaster(String stateId);
}
