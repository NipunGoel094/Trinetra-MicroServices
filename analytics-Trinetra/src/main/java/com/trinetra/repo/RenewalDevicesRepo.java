package com.trinetra.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.RenewalDevices;

@Repository
public interface RenewalDevicesRepo extends JpaRepository<RenewalDevices, Integer>{

	@Query(value ="select * from renewal_devices", nativeQuery = true)
	public Page<Object[]> getRenewalDevicesDetails( Pageable pageable);
	
	@Query(value = "select * from renewal_devices\r\n" + 
			"where device_imei= :searchBy\r\n" + 
			"or device_iccid= :searchBy\r\n" + 
			"or vehicle_reg_no= :searchBy\r\n" + 
			"or primary_mobile_no= :searchBy", nativeQuery = true)
	Page<Object[]> getSearchRenewalDevicesDetails(Pageable pageable, @Param("searchBy") String searchBy);
}
