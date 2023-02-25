package com.trinetra.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.Pincode;

@Repository
public interface PincodeRepo extends JpaRepository<Pincode,Integer> {

    @Query(value = "select * from pincode_master where pincode =  ?1 limit 1",
    nativeQuery = true)
    Optional<Pincode> findByPincode(String pincode);
    
    @Query(value="select pm.pincode,rm.no_of_rto ,sm.\"name\" from rtolocation_master rm join state_master sm on rm.state_id =sm.id join \r\n" + 
    		"pincode_master pm on rm.g_pin_code_id =pm.id where pm.pincode =:pincode",nativeQuery = true)
    Object[][] findDetailsByPincode(@Param("pincode")String pincode);
    
    
}
