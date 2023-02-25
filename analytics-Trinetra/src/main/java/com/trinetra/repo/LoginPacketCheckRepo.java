package com.trinetra.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trinetra.model.entity.LoginPacketCheck;

public interface LoginPacketCheckRepo extends JpaRepository<LoginPacketCheck, Integer>{
	
	@Query(value = "select * from view_login_packet_response where vehicleregno like %:text% or deviceimei like %:text%", nativeQuery = true)
	Page<LoginPacketCheck> getSearchByVehicleRegNoAndDeviceImei(@Param("text") String text, Pageable pageable);
	
	@Query(value = "select * from view_login_packet_response lpr\n"
			+ "inner join device_master dm on dm.device_imei  = lpr.deviceimei\n"
			+ "inner join oem_master om on om.id = dm.oem_id\n"
			+ "where om.id = :oemId", nativeQuery = true)
	Page<LoginPacketCheck> getFilterByCompanyNameWithoutDate(@Param("oemId") int oemId, Pageable pageable);

	@Query(value = "select * from view_login_packet_response lpr\n"
			+ "inner join device_master dm on dm.device_imei  = lpr.deviceimei\n"
			+ "inner join oem_master om on om.id = dm.oem_id\n"
			+ "where om.id = :oemId and lpr.datetime::::date = CAST(:date as date)", nativeQuery = true)
	Page<LoginPacketCheck> getFilterByCompanyNameWithDate(@Param("oemId") int oemId, @Param("date") String date, Pageable pageable);
}
