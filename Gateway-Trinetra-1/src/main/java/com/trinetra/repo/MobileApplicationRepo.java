package com.trinetra.repo;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.VltdActivation;
import com.trinetra.model.page.response.ConnectedDevicesDetailsResponse;
import com.trinetra.model.page.response.DownVehicleResponce;
import com.trinetra.model.page.response.GroupViewVehicleResponse;
import com.trinetra.model.page.response.HistoryLogReportResponse;
//import com.trinetra.model.page.response.LiveEmergencyResponse;
import com.trinetra.model.page.response.MISDealerDetailsForMobile;
import com.trinetra.model.page.response.MisMobileDealerBasicData;
import com.trinetra.model.page.response.MobileConnectedDeviceDetailsResponse;
import com.trinetra.model.page.response.MobileDeviceDetailsResponse;
import com.trinetra.model.page.response.MobileDownVehicleResponse;
import com.trinetra.model.page.response.MobileGroupViewVehicleResponse;
import com.trinetra.model.page.response.MobileHistoryLogReportResponse;
import com.trinetra.model.page.response.MobileLiveEmergencyResponse;
import com.trinetra.model.page.response.MobileRouteDeviationVehicleData;
import com.trinetra.model.page.response.VehicleDetailsForMobile;

@Repository
public interface MobileApplicationRepo extends JpaRepository<VltdActivation, Integer>{

	@Query(value="select \r\n"
			+ "json_agg(json_build_object('vehicleNo',vm.vehicle_reg_no ,'permitHolder',pm.person_name,'deviceImei',dm.device_imei))::::text vehicleList\r\n"
			+ "from	device_master dm \r\n"
			+ "inner join vehicle_device_mapping vdm on vdm.device_id =dm.id \r\n"
			+ "inner join vehicle_master vm on vdm.vehicle_id =vm.id \r\n"
			+ "inner join permitholder_master phm on phm.vehicle_id =vm.id \r\n"
			+ "inner join person_master pm on phm.person_id =pm.id\r\n"
			+ "inner join oem_master om on dm.oem_id =om.id\r\n"
			+ "inner join users u on om.user_id =u.id\r\n"
			+ "where u.id=?1",nativeQuery = true)
	String getDeviceVehicleDataByUserId(long userId);
	
	@Query(value="select rd.id routeId,rd.vehicleregno vehicleRegNo ,rd.starttime startTime,rd.endtime endTime,\r\n"
			+ "rm.route_long_name routeName,dm.device_imei deviceImei,om.name VltdName,pm.person_name permitHolderName,pm.primary_mobile_no permitHolderMobile from routedeviation rd\r\n"
			+ "inner join route_master rm on rd.routeid=rm.id\r\n"
			+ "inner join vehicle_master vm on rd.vehicleregno =vm.vehicle_reg_no and rd.vehicleid =vm.id\r\n"
			+ "inner join person_master pm on vm.person_id =pm.id\r\n"
			+ "inner join vehicle_device_mapping vdm on vdm.vehicle_id =vm.id \r\n"
			+ "inner join device_master dm on vdm.device_id =dm.id\r\n"
			+ "inner join oem_master om on dm.oem_id =om.id\r\n"
			+ "inner join processed_raw_data prd on prd.vehicle_reg_no =rd.vehicleregno  \r\n"
			+ "and prd.string_date_time between rd.starttime and rd.endtime and prd.log_date between rd.starttime::::date and rd.endtime::::date\r\n"
			+ "where vm.vehicle_reg_no =:vehicleRegNo\r\n"
			+ "group by 1,2,3,4,5,6,7,8,9",nativeQuery = true)
	List<MobileRouteDeviationVehicleData> getDeviatedVehicleDataByVehicleRegNo(@Param("vehicleRegNo") String vehicleRegNo);
	
	@Query(value = "select * from (\n"
			+ "select om.name vltdManufacturerName, dm.device_sr_no deviceSerialNo, dm.device_imei deviceImeiNo, \n"
			+ "dm.device_iccid deviceIccidNo, vm.vehicle_reg_no vehicleRegNo, vm.chassis_no vehicleChassisNo, pm2.person_name permitHolderName, \n"
			+ "true panicAlert, a.speed speed, a.alert_lat lat, a.alert_lon lon, a.location location, concat(a.log_date, ' ', a.alert_time)::::timestamp alertGenerationTime, dem.dealer_name dealerName, cvs.string_date_time pollingTime, rm.no_of_rto rtoName, cvs.ignition_status ignition,\n"
			+ "row_number() over (partition by a.imeino order by  a.alert_time desc)\n"
			+ "from alert a \n"
			+ "inner join device_master dm on dm.device_imei = a.imeino\n"
			+ "inner join vehicle_device_mapping vdm on vdm.device_id = dm.id\n"
			+ "inner join vehicle_master vm on vm.id = vdm.vehicle_id\n"
			+ "inner join rtolocation_master rm on rm.id = vdm.registering_rto_location_id\n"
			+ "inner join oem_master om on om.id = dm.oem_id\n"
			+ "inner join permitholder_master pm on pm.vehicle_id = vm.id\n"
			+ "inner join person_master pm2 on pm2.id = pm.person_id\n"
			+ "inner join device_dealer_mapping ddm on ddm.device_id = dm.id\n"
			+ "inner join dealer_master dem on dem.id = ddm.dealer_id\n"
			+ "inner join current_vehicle_string cvs on cvs.imei_no = a.imeino\n"
			+ "inner join customer_address_master cam on cam.person_id = pm2.id\n"
			+ "inner join users u on u.id = cam.user_id\n"
			+ "where a.alert_type_id = 12 and u.user_type_id = 326 and a.log_date = current_date and u.id =:userId and dm.id not in (select er.device_id from emergency_resolve er where er.device_id=dm.id and a.log_date=er.alert_generation_time::::date)\n"
			+ ")th where row_number=1", nativeQuery = true)
	List<MobileLiveEmergencyResponse> getLiveEmergencyListForPermitHolder(@Param("userId") long userId);

	@Query(value = "select * from (\n"
			+ "select om.name vltdManufacturerName, dm.device_sr_no deviceSerialNo, dm.device_imei deviceImeiNo, \n"
			+ "dm.device_iccid deviceIccidNo, vm.vehicle_reg_no vehicleRegNo, vm.chassis_no vehicleChassisNo, pm2.person_name permitHolderName, \n"
			+ "true panicAlert, a.speed speed, a.alert_lat lat, a.alert_lon lon, a.location location, concat(a.log_date, ' ', a.alert_time) alertGenerationTime, dem.dealer_name dealerName, cvs.string_date_time pollingTime, \n"
			+ "row_number() over (partition by a.imeino order by  a.alert_time desc)\n"
			+ "from alert a \n"
			+ "inner join vehicle_master vm on vm.id = a.vehicle_id\n"
			+ "inner join device_master dm on dm.id = a.device_id\n"
			+ "inner join oem_master om on om.id = dm.oem_id\n"
			+ "inner join permitholder_master pm on pm.vehicle_id = vm.id\n"
			+ "inner join person_master pm2 on pm2.id = pm.person_id\n"
			+ "inner join device_dealer_mapping ddm on ddm.device_id = dm.id\n"
			+ "inner join dealer_master dem on dem.id = ddm.dealer_id\n"
			+ "inner join current_vehicle_string cvs on cvs.imei_no = a.imeino\n"
			+ "inner join customer_address_master cam on cam.person_id = pm2.id\n"
			+ "inner join users u on u.id = cam.user_id\n"
			+ "where a.alert_type_id = 12 and u.user_type_id=326 and a.log_date = current_date and dm.id not in (select er.device_id from emergency_resolve er where er.device_id=dm.id and a.log_date=er.alert_generation_time::::date)\n"
			+ "and vm.vehicle_reg_no = :vehicleRegNo \n"
			+ ")th where row_number=1", nativeQuery = true)
	List<MobileLiveEmergencyResponse> getLiveEmergencyListByVehicleRegNoForPermitHolder(@Param("vehicleRegNo")String vehicleRegNo);

	@Query(value="select vm.id vehicleId ,vm.vehicle_reg_no vehicleRegNo,vm.chassis_no chassisNo,dm.device_imei deviceImei,dm.device_iccid deviceIccid,\r\n"
			+ "tm.description vehicleType,vm.engine_no engineNo,pm.person_name personName,pm.primary_mobile_no mobileNo,rm.no_of_rto rtoName, dem.dealer_name dealerName,cvs.lat lat,cvs.lon lon,cvs.string_date_time dateTime,cvs.location location from vehicle_device_mapping vdm \r\n"
			+ "inner join vehicle_master vm on vdm.vehicle_id =vm.id\r\n"
			+ "inner join device_master dm on vm.device_id = dm.id and vdm.device_id =dm.id \r\n"
			+ "inner join type_master tm on vm.vehicle_type_id =tm.id\r\n"
			+ "inner join current_vehicle_string cvs on cvs.vehicle_reg_no =vm.vehicle_reg_no \r\n"
			+ "inner join person_master pm on vm.person_id = pm.id\r\n"
			+ "inner join rtolocation_master rm on rm.id = vm.nodal_agency_id\n"
			+ "inner join dealer_master dem on vm.dealer_id=dem.id\n"
			+ "where cvs.lat>0 and cvs.lon>0 and vm.vehicle_reg_no=:vehicleRegNo",nativeQuery= true)
	public List<VehicleDetailsForMobile> vehicleDetailsByvehicleNo(@Param("vehicleRegNo") String vehicleRegNo);

	@Query(value="select vm.id vehicleId ,vm.vehicle_reg_no vehicleRegNo,vm.chassis_no chassisNo,dm.device_imei deviceImei,dm.device_iccid deviceIccid,\r\n"
			+ "tm.description vehicleType,vm.engine_no engineNo,pm.person_name personName,pm.primary_mobile_no mobileNo,rm.no_of_rto rtoName, dem.dealer_name dealerName,cvs.lat lat,cvs.lon lon,cvs.string_date_time dateTime,cvs.location location from vehicle_device_mapping vdm \r\n"
			+ "inner join vehicle_master vm on vdm.vehicle_id =vm.id\r\n"
			+ "inner join device_master dm on vm.device_id = dm.id and vdm.device_id =dm.id \r\n"
			+ "inner join type_master tm on vm.vehicle_type_id =tm.id\r\n"
			+ "inner join current_vehicle_string cvs on cvs.vehicle_reg_no =vm.vehicle_reg_no \r\n"
			+ "inner join person_master pm on vm.person_id = pm.id\r\n"
			+ "inner join rtolocation_master rm on rm.id = vm.nodal_agency_id\n"
			+ "inner join dealer_master dem on vm.dealer_id=dem.id\n"
			+ "inner join customer_address_master cam on cam.person_id = pm.id\n"
			+ "inner join users u on u.id = cam.user_id\n"
			+ "where cvs.lat>0 and cvs.lon>0 and u.id =:userId",nativeQuery = true)
	List<VehicleDetailsForMobile> vehicleDetailsByvehicleNo(@Param("userId") long userId);
	
	@Query(value="select vm.id vehicleId ,vm.vehicle_reg_no vehicleRegNo,vm.chassis_no chassisNo,dm.device_imei deviceImei,dm.device_iccid deviceIccid,\r\n"
			+ "tm.description vehicleType,vm.engine_no engineNo,pm.person_name personName,pm.primary_mobile_no mobileNo,rm.no_of_rto rtoName, dem.dealer_name dealerName,cvs.lat lat,cvs.lon lon,cvs.string_date_time dateTime,cvs.location location from vehicle_device_mapping vdm \r\n"
			+ "inner join vehicle_master vm on vdm.vehicle_id =vm.id\r\n"
			+ "inner join device_master dm on vm.device_id = dm.id and vdm.device_id =dm.id \r\n"
			+ "inner join type_master tm on vm.vehicle_type_id =tm.id\r\n"
			+ "inner join current_vehicle_string cvs on cvs.vehicle_reg_no =vm.vehicle_reg_no \r\n"
			+ "inner join person_master pm on vm.person_id = pm.id\r\n"
			+ "inner join rtolocation_master rm on rm.id = vm.nodal_agency_id\n"
			+ "inner join dealer_master dem on vm.dealer_id=dem.id\n"
			+ "where cvs.lat>0 and cvs.lon>0",nativeQuery = true)
	List<VehicleDetailsForMobile> vehicleDetailsByvehicleNo();
	
	@Query(value = "select * from (\n"
			+ "select om.name vltdManufacturerName, dm.device_sr_no deviceSerialNo, dm.device_imei deviceImeiNo, \n"
			+ "dm.device_iccid deviceIccidNo, vm.vehicle_reg_no vehicleRegNo, vm.chassis_no vehicleChassisNo, pm2.person_name permitHolderName, \n"
			+ "true panicAlert, a.speed speed, a.alert_lat lat, a.alert_lon lon, a.location location, concat(a.log_date, ' ', a.alert_time)::::timestamp alertGenerationTime, dem.dealer_name dealerName, cvs.string_date_time pollingTime, rm.no_of_rto rtoName, cvs.ignition_status ignition,\n"
			+ "row_number() over (partition by a.imeino order by  a.alert_time desc)\n"
			+ "from alert a \n"
			+ "inner join device_master dm on dm.device_imei = a.imeino\n"
			+ "inner join vehicle_device_mapping vdm on vdm.device_id = dm.id\n"
			+ "inner join vehicle_master vm on vm.id = vdm.vehicle_id\n"
			+ "inner join rtolocation_master rm on rm.id = vdm.registering_rto_location_id\n"
			+ "inner join oem_master om on om.id = dm.oem_id\n"
			+ "inner join permitholder_master pm on pm.vehicle_id = vm.id\n"
			+ "inner join person_master pm2 on pm2.id = pm.person_id\n"
			+ "inner join device_dealer_mapping ddm on ddm.device_id = dm.id\n"
			+ "inner join dealer_master dem on dem.id = ddm.dealer_id\n"
			+ "inner join current_vehicle_string cvs on cvs.imei_no = a.imeino\n"
			+ "where a.alert_type_id = 12 and a.log_date = current_date and dm.id not in (select er.device_id from emergency_resolve er where er.device_id=dm.id and a.log_date=er.alert_generation_time::::date)\n"
			+ ")th where row_number=1", nativeQuery = true)
	public List<MobileLiveEmergencyResponse> getLiveEmergencyListForPoliceUser();
	
	@Query(value = "select * from (\n"
			+ "select om.name vltdManufacturerName, dm.device_sr_no deviceSerialNo, dm.device_imei deviceImeiNo, \n"
			+ "dm.device_iccid deviceIccidNo, vm.vehicle_reg_no vehicleRegNo, vm.chassis_no vehicleChassisNo, pm2.person_name permitHolderName, \n"
			+ "true panicAlert, a.speed speed, a.alert_lat lat, a.alert_lon lon, a.location location, concat(a.log_date, ' ', a.alert_time)::::timestamp alertGenerationTime, dem.dealer_name dealerName, cvs.string_date_time pollingTime, rm.no_of_rto rtoName, cvs.ignition_status ignition,\n"
			+ "row_number() over (partition by a.imeino order by  a.alert_time desc)\n"
			+ "from alert a \n"
			+ "inner join device_master dm on dm.device_imei = a.imeino\n"
			+ "inner join vehicle_device_mapping vdm on vdm.device_id = dm.id\n"
			+ "inner join vehicle_master vm on vm.id = vdm.vehicle_id\n"
			+ "inner join rtolocation_master rm on rm.id = vdm.registering_rto_location_id\n"
			+ "inner join oem_master om on om.id = dm.oem_id\n"
			+ "inner join permitholder_master pm on pm.vehicle_id = vm.id\n"
			+ "inner join person_master pm2 on pm2.id = pm.person_id\n"
			+ "inner join device_dealer_mapping ddm on ddm.device_id = dm.id\n"
			+ "inner join dealer_master dem on dem.id = ddm.dealer_id\n"
			+ "inner join current_vehicle_string cvs on cvs.imei_no = a.imeino\n"
			+ "where a.alert_type_id = 12 and a.log_date = current_date and dm.id not in (select er.device_id from emergency_resolve er where er.device_id=dm.id and a.log_date=er.alert_generation_time::::date)\n"
			+ "and vm.vehicle_reg_no =:vehicleRegNo\n"
			+ ")th where row_number=1", nativeQuery = true)
	public List<MobileLiveEmergencyResponse> getLiveEmergencyListByVehicleNo(@Param("vehicleRegNo") String vehicleRegNo);
	
	@Query(value = "select\r\n"
			+ "        d.device_imei deviceImei,\r\n"
			+ "        d.device_sr_no deviceSrNo,\r\n"
			+ "        d.device_iccid deviceIccid,\r\n"
			+ "        v.vehicle_reg_no vehicleRegNo,\r\n"
			+ "        v.chassis_no chassisNo,\r\n"
			+ "        rlm.no_of_rto rtoName,\r\n"
			+ "        p.person_name permitHolderName,\r\n"
			+ "        o.name manufacturerName,\r\n"
			+ "        dlm.dealer_name dealerName,\r\n"
			+ "        p.primary_mobile_no primaryMobileNumber,\r\n"
			+ "        cvs.string_date_time pollingTime,\r\n"
			+ "        (case when cvs.string_date_time::::date=current_date then 'Active' else 'Inactive' end) status, \r\n"
			+ "		   cvs.lat lat, cvs.lon lon, cvs.location location, cvs.speed speed, cvs.ignition_status ignition\r\n"
			+ "    from\r\n"
			+ "        vehicle_master AS v         \r\n"
			+ "    inner join\r\n"
			+ "        vehicle_device_mapping AS vm                            \r\n"
			+ "            on v.id=vm.vehicle_id      \r\n"
			+ "    inner join\r\n"
			+ "        device_master AS d                            \r\n"
			+ "            on  vm.device_id=d.id       \r\n"
			+ "    inner join\r\n"
			+ "        oem_master AS o                          \r\n"
			+ "            on  o.id=d.oem_id      \r\n"
			+ "    inner join\r\n"
			+ "        person_master AS  p                   \r\n"
			+ "            on p.id=v.person_id     \r\n"
			+ "    inner join\r\n"
			+ "        rtolocation_master AS rlm   \r\n"
			+ "            on      rlm.id= vm.registering_rto_location_id   \r\n"
			+ "    inner join\r\n"
			+ "        device_dealer_mapping AS ddm   \r\n"
			+ "            on      ddm.device_id=d.id   \r\n"
			+ "    inner join\r\n"
			+ "        dealer_master AS dlm   \r\n"
			+ "            on dlm.id=ddm.dealer_id "
			+ "	   inner join current_vehicle_string cvs on cvs.imei_no = d.device_imei "
			+ "	   inner join users u on u.id = o.user_id where u.id = :userId and u.user_type_id=2 order by cvs.string_date_time desc", nativeQuery = true)
	public Page<MobileDeviceDetailsResponse> getDeviceList(Pageable pageable, @Param("userId") long userId);
	
	@Query(value = "select  sum(count(*)) over()\n"
			+ "    from\n"
			+ "        vehicle_master AS v         \n"
			+ "    inner join\n"
			+ "        vehicle_device_mapping AS vm  \n"
			+ "            on v.id=vm.vehicle_id      \n"
			+ "    inner join\n"
			+ "        device_master AS d                            \n"
			+ "            on  vm.device_id=d.id       \n"
			+ "    inner join\n"
			+ "        oem_master AS o                          \n"
			+ "            on  o.id=d.oem_id      \n"
			+ "    inner join\n"
			+ "        person_master AS  p                   \n"
			+ "            on p.id=v.person_id     \n"
			+ "    inner join\n"
			+ "        rtolocation_master AS rlm   \n"
			+ "            on rlm.id= vm.registering_rto_location_id   \n"
			+ "    inner join\n"
			+ "        device_dealer_mapping AS ddm   \n"
			+ "            on ddm.device_id=d.id   \n"
			+ "    inner join\n"
			+ "        dealer_master AS dlm   \n"
			+ "            on dlm.id=ddm.dealer_id  "
			+ "	   inner join current_vehicle_string cvs on cvs.imei_no = d.device_imei "
			+ "	   inner join users u on u.id = o.user_id where u.id = :userId and u.user_type_id=2", nativeQuery = true)
	public int getDeviceListCount(@Param("userId") long userId);
	
	@Query(value = "select\r\n"
			+ "        d.device_imei deviceImei,\r\n"
			+ "        d.device_sr_no deviceSrNo,\r\n"
			+ "        d.device_iccid deviceIccid,\r\n"
			+ "        v.vehicle_reg_no vehicleRegNo,\r\n"
			+ "        v.chassis_no chassisNo,\r\n"
			+ "        rlm.no_of_rto rtoName,\r\n"
			+ "        p.person_name permitHolderName,\r\n"
			+ "        o.name manufacturerName,\r\n"
			+ "        dlm.dealer_name dealerName,\r\n"
			+ "        p.primary_mobile_no primaryMobileNumber,\r\n"
			+ "        cvs.string_date_time pollingTime,\r\n"
			+ "        (case when cvs.string_date_time::::date=current_date then 'Active' else 'Inactive' end) status, \r\n"
			+ "		   cvs.lat lat, cvs.lon lon, cvs.location location, cvs.speed speed, cvs.ignition_status ignition\r\n"
			+ "    from\r\n"
			+ "        vehicle_master AS v         \r\n"
			+ "    inner join\r\n"
			+ "        vehicle_device_mapping AS vm                            \r\n"
			+ "            on v.id=vm.vehicle_id      \r\n"
			+ "    inner join\r\n"
			+ "        device_master AS d                            \r\n"
			+ "            on  vm.device_id=d.id       \r\n"
			+ "    inner join\r\n"
			+ "        oem_master AS o                          \r\n"
			+ "            on  o.id=d.oem_id      \r\n"
			+ "    inner join\r\n"
			+ "        person_master AS  p                   \r\n"
			+ "            on p.id=v.person_id     \r\n"
			+ "    inner join\r\n"
			+ "        rtolocation_master AS rlm   \r\n"
			+ "            on      rlm.id= vm.registering_rto_location_id   \r\n"
			+ "    inner join\r\n"
			+ "        device_dealer_mapping AS ddm   \r\n"
			+ "            on      ddm.device_id=d.id   \r\n"
			+ "    inner join\r\n"
			+ "        dealer_master AS dlm   \r\n"
			+ "            on dlm.id=ddm.dealer_id \r\n"
			+ "	   inner join current_vehicle_string cvs on cvs.imei_no = d.device_imei \r\n"
			+ "    where lower(d.device_imei) =:imeiNo\n"
			+ "	   order by cvs.string_date_time desc", nativeQuery = true)
	public List<MobileDeviceDetailsResponse> findByImeiId(@Param("imeiNo") String imeiNo);
	
	@Query(value = "select dealer_name from dealer_master where  oem_id =:oemId", nativeQuery = true)
	public List<Object> findDeafultDealerForOEM(@Param("oemId")BigInteger  oemId);
	
	@Query(value = "select vm.vehicle_reg_no vehicleRegNo, dm.device_sr_no deviceSrNo, dm.device_iccid deviceIccid, rm.no_of_rto rtoName, dem.dealer_name dealerName, om.name manufacturerName, pm.person_name permitHolderName, pm.primary_mobile_no primaryMobileNumber, vm.chassis_no chassisNo, cvs.string_date_time pollingTime, cvs.lat lat, cvs.lon lon, cvs.\"location\" \"location\", cvs.speed speed, cvs.imei_no deviceImei, cvs.ignition_status ignition\n"
			+ "from current_vehicle_string cvs \n"
			+ "inner join device_master dm on dm.device_imei = cvs.imei_no\n"
			+ "inner join vehicle_device_mapping vdm on vdm.device_id = dm.id\n"
			+ "inner join vehicle_master vm on vm.id = vdm.vehicle_id\n"
			+ "inner join oem_master om on om.id = dm.oem_id\n"
			+ "inner join person_master pm on pm.id = vm.person_id\n"
			+ "inner join device_dealer_mapping ddm on ddm.device_id = dm.id\n"
			+ "inner join dealer_master dem on dem.id = ddm.dealer_id\n"
			+ "inner join rtolocation_master rm on rm.id = vdm.registering_rto_location_id\n"
			+ "inner join users u on u.id = om.user_id\n"
			+ "where u.id = :userId and u.user_type_id=2 and cvs.string_date_time::::date = current_date\n"
			+ "order by cvs.string_date_time desc", nativeQuery = true)
	public List<MobileDeviceDetailsResponse> getConnectedDevicesList(@Param("userId") long userId);
	
	@Query(value = "select oem.name manufacturerName,vm.vehicle_reg_no vehicleRegNo,vm.chassis_no chassisNo, dm.device_sr_no deviceSrNo,\n"
			+ "		dm.device_iccid deviceIccid,ct.imei_no deviceImei,pm.person_name permitHolderName,pm.primary_mobile_no primaryMobileNumber,rm.no_of_rto rtoName,ct.string_date_time pollingTime,\n"
			+ "		ct.speed speed,ct.location location,\n"
			+ "		(ct.Lat)lat,(ct.lon)lon,ct.ignition_status ignition,dem.dealer_name dealerName  \n"
			+ "		FROM current_vehicle_string  ct\n"
			+ "		inner join device_master dm on   ct.imei_no=dm.device_imei \n"
			+ "		inner join oem_master oem  on dm.oem_id = oem.id\n"
			+ "		inner join vehicle_device_mapping vdm on vdm.device_id = dm.id\n"
			+ "		inner join vehicle_master vm  on vdm.vehicle_id=vm.id\n"
			+ "		inner join rtolocation_master rm on rm.id = vdm.registering_rto_location_id\n"
			+ "		inner join person_master pm on vm.person_id=pm.id\n"
			+ "		inner join device_dealer_mapping ddm on ddm.device_id = dm.id\n"
			+ "		inner join dealer_master dem on dem.id = ddm.dealer_id\n"
			+ "		inner join users u on u.id = oem.user_id\n"
			+ "		where u.id = :userId and u.user_type_id=2 and ct.string_date_time::::date < current_date\n"
			+ "		order by ct.string_date_time desc", nativeQuery = true)
	public List<MobileDeviceDetailsResponse> getDownVehicleList(@Param("userId") long userId);

	@Query(value = "select t1.vltdName,t1.dealerName,t1.permitHolderName,t1.rtoName,\r\n"
			+ "t1.deviceUin,t1.deviceImei,t1.deviceIccid,t1.vehicleNo,t1.pollingDateTime,t1.speed,t1.eventLat,t1.ignitionStatus,t1.vehicleStatus,t1.gpsStatus,t1.eventLong,t1.eventLocation,t2.alert from\r\n"
			+ "(select (om.name)vltdName,(dem.dealer_name)dealerName,(pm.person_name)permitHolderName,(rtm.no_of_rto)rtoName,(dm.device_imei)deviceImei,\r\n"
			+ "(dm.device_sr_no)deviceUin,(dm.device_iccid)deviceIccid,(vm.vehicle_reg_no)vehicleNo,(cvs.string_date_time)pollingDateTime,\r\n"
			+ "(cvs.speed)speed,(cvs.lat)eventLat,(cvs.ignition_status)ignitionStatus,(case when cvs.ignition_status=true then 'Running' else 'Stop' end)vehicleStatus,(cvs.gps_status)gpsStatus,(cvs.lon)eventLong,(cvs.location)eventLocation\r\n"
			+ "from current_vehicle_string cvs\r\n"
			+ "inner join device_master dm on cvs.device_id::::integer=dm.id \r\n"
			+ "inner join oem_master om on dm.oem_id=om.id\r\n"
			+ "inner join vehicle_device_mapping vdm on dm.id=vdm.device_id \r\n"
			+ "inner join rtolocation_master rtm on vdm.registering_rto_location_id=rtm.id\r\n"
			+ "inner join vehicle_master vm on vm.id = cvs.vehicle_id::::integer\r\n"
			+ "inner join permitholder_master phm on vm.person_id=phm.person_id\r\n"
			+ "inner join person_master pm on phm.person_id=pm.id\r\n"
			+ "inner join dealer_master dem on vm.dealer_id=dem.id\r\n"
			+ "inner join customer_address_master cam on cam.person_id = pm.id\r\n"
			+ "inner join users u on u.id = cam.user_id\r\n"
			+ "where u.id=:userId and u.user_type_id=326 order by cvs.string_date_time desc)t1\r\n"
			+ "left join lateral(\r\n"
			+ "select (at.log_date||' '||at.alert_time)::::timestamp without time zone at_eventdate,(atm.alert_name)alert from \r\n"
			+ "alert at  \r\n"
			+ "inner join alert_type_master atm on at.alert_type_id=atm.id \r\n"
			+ "where at.imeino=t1.deviceImei and t1.pollingDateTime = (at.log_date||' '||at.alert_time)::::timestamp without time zone  order by t1.pollingDateTime desc--and t1.pollingDateTime::date=at.log_date \r\n"
			+ ")t2 on true", nativeQuery = true)
	List<MobileHistoryLogReportResponse> getAllHistoryLogReport(@Param("userId") long userId);
	
	@Query(value="select t1.vltdName,t1.dealerName,t1.permitHolderName,t1.rtoName,\r\n"
			+ "t1.deviceUin,t1.deviceImei,t1.deviceIccid,t1.vehicleNo,t1.pollingDateTime,t1.speed,t1.eventLat,t1.ignitionStatus,t1.vehicleStatus,t1.gpsStatus,t1.eventLong,t1.eventLocation,t2.alert from\r\n"
			+ "(select (om.name)vltdName,(dem.dealer_name)dealerName,(pm.person_name)permitHolderName,(rtm.no_of_rto)rtoName,(dm.device_imei)deviceImei,\r\n"
			+ "(dm.device_sr_no)deviceUin,(dm.device_iccid)deviceIccid,(vm.vehicle_reg_no)vehicleNo,(prd.string_date_time)pollingDateTime,\r\n"
			+ "(prd.speed)speed,(prd.lat)eventLat,(prd.ignition_status)ignitionStatus,(case when prd.ignition_status=true then 'Running' else 'Stop' end)vehicleStatus,(prd.gps_status)gpsStatus,(prd.lon)eventLong,(prd.location)eventLocation,prd.log_date log_date \r\n"
			+ "from processed_raw_data prd\r\n"
			+ "inner join device_master dm on prd.device_id::::integer=dm.id \r\n"
			+ "inner join oem_master om on dm.oem_id=om.id\r\n"
			+ "inner join vehicle_device_mapping vdm on dm.id=vdm.device_id \r\n"
			+ "inner join rtolocation_master rtm on vdm.registering_rto_location_id=rtm.id\r\n"
			+ "inner join vehicle_master vm on prd.vehicle_id=vm.id \r\n"
			+ "inner join permitholder_master phm on vm.person_id=phm.person_id\r\n"
			+ "inner join person_master pm on phm.person_id=pm.id\r\n"
			+ "inner join dealer_master dem on vm.dealer_id=dem.id \r\n"
			+ "where vm.vehicle_reg_no=:vehicleRegNo\r\n"
			+ "and prd.string_date_time BETWEEN cast(:startDateTime as timestamp) AND cast(:endDateTime as timestamp)\r\n"
			+ "and prd.log_date between  cast(:startDateTime as date) AND cast(:endDateTime as date) order by prd.string_date_time desc)t1\r\n"
			+ "left join lateral (\r\n"
			+ "select (at.log_date||' '||at.alert_time)::::timestamp without time zone at_eventdate,(atm.alert_name)alert from \r\n"
			+ "alert at  \r\n"
			+ "inner join alert_type_master atm on at.alert_type_id=atm.id \r\n"
			+ "where at.imeino=t1.deviceImei and t1.pollingDateTime = (at.log_date||' '||at.alert_time)::::timestamp without time zone and t1.log_date=at.log_date\r\n"
			+ "and at.log_date between  cast(:startDateTime as date) AND cast(:endDateTime as date)\r\n"
			+ ")t2 on true", nativeQuery=true)
	List<MobileHistoryLogReportResponse> getHistoryLogReport(@Param("vehicleRegNo") String vehicleRegNo,@Param("startDateTime") String startDateTime,@Param("endDateTime") String endDateTime);
	
	@Query(value = "select count(*) from vehicle_master vm inner join vehicle_device_mapping vdm on vdm.vehicle_id = vm.id where vm.vehicle_reg_no =:vehicleRegNo", nativeQuery = true)
	int checkIfVehicleMapped(@Param("vehicleRegNo") String vehicleRegNo);
	
	@Query(value="select rd.id routeId,rd.vehicleregno vehicleRegNo ,rd.starttime startTime,rd.endtime endTime,\r\n"
			+ "rm.route_long_name routeName,dm.device_imei deviceImei,om.name VltdName,pm.person_name permitHolderName,pm.primary_mobile_no permitHolderMobile from routedeviation rd\r\n"
			+ "inner join route_master rm on rd.routeid=rm.id\r\n"
			+ "inner join vehicle_master vm on rd.vehicleregno =vm.vehicle_reg_no and rd.vehicleid =vm.id\r\n"
			+ "inner join person_master pm on vm.person_id =pm.id\r\n"
			+ "inner join vehicle_device_mapping vdm on vdm.vehicle_id =vm.id \r\n"
			+ "inner join device_master dm on vdm.device_id =dm.id\r\n"
			+ "inner join oem_master om on dm.oem_id =om.id\r\n"
			+ "inner join processed_raw_data prd on prd.vehicle_reg_no =rd.vehicleregno  \r\n"
			+ "and prd.string_date_time between rd.starttime and rd.endtime and prd.log_date between rd.starttime::::date and rd.endtime::::date\r\n"
			+ "group by 1,2,3,4,5,6,7,8,9",nativeQuery = true)
	List<MobileRouteDeviationVehicleData> getDeviatedVehicleData();
	
    @Query(value="select cvs.lat lat,cvs.lon lon,cvs.location location ,cvs.string_date_time dateTime,cvs.vehicle_reg_no vehicleRegNo ,cvs.speed speed,rm.no_of_rto rtoName, om.name vltdManufacturerName, cvs.ignition_status ignition \r\n"
    		+ "        from current_vehicle_string cvs\r\n"
    		+ "        inner join vehicle_master vm on cvs.vehicle_id=vm.id\r\n"
    		+ "        inner join vehicle_device_mapping vdm on vdm.vehicle_id =vm.id \r\n"
    		+ "        inner join device_master dm on   vdm.device_id =dm.id\r\n"
    		+ "        inner join oem_master om on dm.oem_id =om.id\r\n"
    		+ "        inner join address_master am on om.address_id =am.id \r\n"
    		+ "        inner join pincode_master pm on am.pincode_id =pm.id\r\n"
    		+ "        inner join rtolocation_master rm on vdm.registering_rto_location_id =rm.id\r\n"
    		+ "        inner join state_master sm on pm.state_id =sm.id \r\n"
    		+ "        where cvs.vehicle_reg_no is not null and cvs.lat>0 and cvs.lon>0 and sm.id=?1 and rm.id=?2",nativeQuery = true)
	List<MobileGroupViewVehicleResponse> getDetailsByStateIdAndRto(int stateId, int rtoId);

    @Query(value="select dmm.id dealerId,dmm.dealer_name dealerName ,dmm.dealer_code dealerCode,count(ddm.device_id) deviceCount from device_master dm \r\n"
    		+ "inner join oem_master om on dm.oem_id =om.id \r\n"
    		+ "inner join device_dealer_mapping ddm on ddm.device_id =dm.id \r\n"
    		+ "inner join dealer_master dmm on ddm.dealer_id =dmm.id\r\n"
    		+ "inner join users u on om.user_id =u.id\r\n"
    		+ "where u.id=?1\r\n"
    		+ "group by dmm.id,dmm.dealer_name,dmm.dealer_code",nativeQuery = true)
	List<MisMobileDealerBasicData> getMISDealerDatabyOemUser(int userid);

    @Query(value="select dlm.dealer_name dealerName,pm2.person_name contactPerson,pm2.primary_mobile_no mobileNumber ,pm2.email_id emailId ,\r\n"
    		+ "dlm.dealer_code dealerCode ,concat(am.address1,' ',am.address2) address , count(vdm.id)totalDevice, (count(vdm.id) filter(where vdm.is_active=true) + count(vdm.id) filter(where vdm.is_active=false)) installedDevice,\r\n"
    		+ "count(vdm.id) filter(where vdm.is_active=true)activatedDevice, count(vdm.id) filter(where vdm.is_active=false) pendingDevice, \r\n"
    		+ "count(vdm.id) filter(where cvs.string_date_time is not null and (SELECT DATE_PART('day', cast(now() AS timestamp) - cvs.string_date_time) * 24 +DATE_PART('hour', cast(now() AS timestamp) - cvs.string_date_time)) <=  24)connectedDevice,\r\n"
    		+ "((count(vdm.id) filter(where vdm.is_active=true)) - (count(vdm.id) filter(where cvs.string_date_time is not null and (SELECT DATE_PART('day', cast(now() AS timestamp) - cvs.string_date_time) * 24 +DATE_PART('hour', cast(now() AS timestamp) - cvs.string_date_time)) <=  24)))notConnectedDevice \r\n"
    		+ "from dealer_master dlm \r\n"
    		+ "left join device_dealer_mapping ddm on ddm.dealer_id = dlm.id\r\n"
    		+ "left join device_master dm on dm.id = ddm.device_id \r\n"
    		+ "left join oem_master om on dm.oem_id =om.id\r\n"
    		+ "left join users u on om.user_id =u.id\r\n"
    		+ "left join vehicle_device_mapping vdm on vdm.device_id = dm.id \r\n"
    		+ "left join rtolocation_master rm on rm.id = vdm.registering_rto_location_id  \r\n"
    		+ "left join permitholder_master pm on pm.vehicle_id = vdm.vehicle_id\r\n"
    		+ "left join person_master pm2 on dlm.person_id =pm2.id\r\n"
    		+ "left join address_master am on dlm.address_id =am.id\r\n"
    		+ "left join current_vehicle_string cvs on cvs.imei_no = dm.device_imei\r\n"
    		+ "where dlm.is_active=true and dlm.dealer_code =?1\r\n"
    		+ "group by dlm.dealer_name,dlm.id,pm2.primary_mobile_no ,pm2.email_id ,dlm.dealer_code,pm2.person_name,am.address1,am.address2\r\n"
    		+ "having count(vdm.id) > 0\r\n"
    		+ "order by dlm.id",nativeQuery = true)
	List<MISDealerDetailsForMobile> findBydealerCode(String dealerCode);
}
