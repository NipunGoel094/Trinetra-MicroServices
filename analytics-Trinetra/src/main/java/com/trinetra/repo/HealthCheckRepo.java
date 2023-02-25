package com.trinetra.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.HealthCheck;

@Repository
public interface HealthCheckRepo extends JpaRepository<HealthCheck, Integer>{
    
    @Query(value = "select * from view_health_check where device_imei like %:text%", nativeQuery = true)
    public Page<HealthCheck> getSearchResults(@Param("text") String text, Pageable pageable);
    
    @Query(value = "select * from view_health_check hc inner join device_master dm on dm.device_imei = hc.device_imei\n"
    		+ "inner join oem_master om on om.id = dm.oem_id\n"
    		+ "where om.id = :oemId order by hc.recieved_date_time desc", nativeQuery = true)
    public Page<HealthCheck> getFilteredDataByCompanyWithoutDate(@Param("oemId") int oemId, Pageable pageable);
    
    @Query(value = "select * from view_health_check hc inner join device_master dm on dm.device_imei = hc.device_imei\n"
    		+ "inner join oem_master om on om.id = dm.oem_id\n"
    		+ "where om.id = :oemId and hc.log_date = CAST(:date as date) order by hc.recieved_date_time desc", nativeQuery = true)
    public Page<HealthCheck> getFilteredDataByCompanyWithDate(@Param("oemId") int oemId, @Param("date") String date, Pageable pageable);

    @Query(value = "select * from view_health_check hc inner join device_master dm on dm.device_imei = hc.device_imei\n"
    		+ "inner join device_dealer_mapping ddm on dm.id = ddm.device_id \r\n"
    		+ "inner join dealer_master dm2 on ddm.dealer_id = dm2.id \r\n"
    		+ "inner join rtolocation_master rlm on rlm.id = dm2.rto_location_id\r\n"
    		+ "where rlm.id = :rtoLocationId order by hc.recieved_date_time desc", nativeQuery = true)
	public List<HealthCheck> getFilteredDataByRtoWithoutDate(@Param("rtoLocationId") int rtoLocationId);

    @Query(value = "select * from view_health_check hc inner join device_master dm on dm.device_imei = hc.device_imei\n"
    		+ "inner join device_dealer_mapping ddm on dm.id = ddm.device_id \r\n"
    		+ "inner join dealer_master dm2 on ddm.dealer_id = dm2.id \r\n"
    		+ "inner join rtolocation_master rlm on rlm.id = dm2.rto_location_id\r\n"
    		+ "where rlm.id = :rtoLocationId and hc.log_date = CAST(:date as date) order by hc.recieved_date_time desc", nativeQuery = true)
	public List<HealthCheck> getFilteredDataByRtoWithDate(@Param("rtoLocationId") int rtoLocationId, @Param("date") String date);
    
    @Query(value="select imei from public.ais_health_packet_latest where imei=:imei",nativeQuery = true)
	public String checkImeiExits(@Param("imei") String imei);

    @Query(value="SELECT  CONCAT(start_character,header, vendor_id,firmware_version,imei, battery_percentage,low_battery_threshold_value,memory_percentage,data_update_rate_when_ignition_on,data_update_rate_when_ignition_off,digital_input1,digital_input2,digital_input3,digital_input4,digital_output1,digital_output2,analog_input1,analog_input2,checksum,end_character,log_date,recieved_date_time)\r\n"
    		+ "FROM public.ais_health_packet_latest where imei=:imei",nativeQuery = true)
	public String getHealthRawDataByImei(@Param("imei") String imei);

}
