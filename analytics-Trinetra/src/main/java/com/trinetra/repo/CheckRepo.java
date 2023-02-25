package com.trinetra.repo;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.master.DealerMaster;

@Repository
public interface CheckRepo extends JpaRepository<DealerMaster, Long>{
	
	@Query(value = "select dealer_name from dealer_master where  oem_id =:oemId", nativeQuery = true)
	public List<Object> findDeafultDealerForOEM(@Param("oemId")BigInteger  oemId);	
	
}
