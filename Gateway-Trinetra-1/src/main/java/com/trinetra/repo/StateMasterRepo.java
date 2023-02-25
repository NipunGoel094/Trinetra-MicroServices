package com.trinetra.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.master.StateMaster;

@Repository
public interface StateMasterRepo extends JpaRepository<StateMaster,Integer> {
	
	@Query(value = "select sm.id from state_master sm \r\n"
			+ "inner join pincode_master pm on sm.id = pm.state_id \r\n"
			+ "inner join address_master am on pm.id = am.pincode_id \r\n"
			+ "inner join users u on am.id = u.address_id \r\n"
			+ "where u.id =:userId", nativeQuery = true)
	 Optional<String> getStateName(@Param("userId") Long userId);
	 
}
