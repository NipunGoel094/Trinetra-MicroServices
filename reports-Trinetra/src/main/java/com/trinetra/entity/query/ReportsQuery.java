package com.trinetra.entity.query;

public class ReportsQuery {
public ReportsQuery() {
	
}	
	public static final String GET_EMERGENCY_RESOLVED_ALERT_REPORT="select om.name vltdname, dem.dealer_name dealername, dm.id deviceId, dm.device_imei deviceimei, dm.device_uin deviceuin, dm.device_iccid deviceiccid, \n"
			+ "vm.vehicle_reg_no vehicleregno, er.alert_type alerttype, er.alert_generation_time alertGenerationTime, er.resolved_time resolvedTime, er.resolver_mobile_no resolverMobileNo, er.location location, er.latitude latitude, er.longitude longitude \n"
			+ "from emergency_resolve er\n"
			+ "left join device_master dm on dm.id = er.device_id\n"
			+ "left join vehicle_device_mapping vdm on vdm.device_id = dm.id\n"
			+ "left join vehicle_master vm on vm.id = vdm.vehicle_id\n"
			+ "left join oem_master om on om.id = dm.oem_id\n"
			+ "left join device_dealer_mapping ddm on ddm.device_id = dm.id\n"
			+ "left join dealer_master dem on dem.id = ddm.dealer_id\n"
			+ "order by er.alert_generation_time desc";
	
	public static final String GET_EMERGENCY_RESOLVED_ALERT_REPORT_BY_FILTER="select om.name vltdname, dem.dealer_name dealername, dm.id deviceId, dm.device_imei deviceimei, dm.device_uin deviceuin, dm.device_iccid deviceiccid, \n"
			+ "vm.vehicle_reg_no vehicleregno, er.alert_type alerttype, er.alert_generation_time alertGenerationTime, er.resolved_time resolvedTime, er.resolver_mobile_no resolverMobileNo, er.location location, er.latitude latitude, er.longitude longitude \n"
			+ "from emergency_resolve er\n"
			+ "left join device_master dm on dm.id = er.device_id\n"
			+ "left join vehicle_device_mapping vdm on vdm.device_id = dm.id\n"
			+ "left join vehicle_master vm on vm.id = vdm.vehicle_id\n"
			+ "left join oem_master om on om.id = dm.oem_id\n"
			+ "left join device_dealer_mapping ddm on ddm.device_id = dm.id\n"
			+ "left join dealer_master dem on dem.id = ddm.dealer_id\n"
			+ "where om.name=:vltdName and vm.vehicle_reg_no=:vehicleRegNo and er.alert_generation_time between cast(:startDate as timestamp) and cast(:endDate as timestamp)\n"
			+ "order by er.alert_generation_time desc";
	
	public static final String GET_OVER_SPEED_ALERT_REPORT="select distinct on(a.alert_time) om.name vltdName,dem.dealer_name dealerName,pm.person_name permitHolderName,rlm.no_of_rto rtoName,dm.device_imei deviceImei,dm.device_iccid deviceIccid,\n"
			+ "vm.vehicle_reg_no vehicleRegNo,atm.alert_name alertType,(MIN((concat(a.log_date,':',a.alert_time))))startDateTime,(MAX((concat(a.log_date,':',a.alert_time))))endDateTime,(MAX(a.speed))speed,\n"
			+ "(cvs.ignition_status) ignitionStatus,(case when cvs.ignition_status=true then 'Running' when cvs.ignition_status=false then 'Stop' else 'Not Known' end)vehicleStatus,(cvs.gps_status)gpsStatus,concat(a.alert_lat , ',', a.alert_lon)overAlertLatlong,(a.location)alertLocation\n"
			+ "from alert a\n"
			+ "left join alert_type_master atm on a.alert_type_id =atm.id \n"
			+ "left join device_master dm on a.imeino =dm.device_imei \n"
			+ "left join vehicle_master vm on dm.id=vm.device_id \n"
			+ "left join vehicle_device_mapping vdm on vm.id=vdm.vehicle_id  and dm.id=vdm.device_id \n"
			+ "left join dealer_master dem on vm.dealer_id =dem.id \n"
			+ "left join oem_master om on dm.oem_id =om.id \n"
			+ "left join person_master pm on vm.person_id =pm.id \n"
			+ "left join current_vehicle_string cvs on a.imeino =cvs.imei_no and vm.id=cvs.vehicle_id and dm.device_imei =cvs.imei_no \n"
			+ "left join rtolocation_master rlm on vdm.registering_rto_location_id  = rlm.id\n"
			+ "where a.vehicle_id = vdm.vehicle_id and a.alert_type_id = 4 and a.log_date = current_date\n"
			+ "group by om.name ,dem.dealer_name ,pm.person_name ,rlm.no_of_rto ,dm.device_imei ,dm.device_iccid,\n"
			+ "vm.vehicle_reg_no,atm.alert_name,cvs.ignition_status,cvs.gps_status,a.location,a.alert_lat,a.alert_lon,a.alert_time";
	
	public static final String GET_ROUTE_DEVIATION_REPORT="select distinct on(allother.startLocation,endLocation.endLocation) \n"
			+ "allother.vltdName,allother.vehicleRegNo,allother.routeName,allother.rtoName,allother.startTime,allother.endTime\n"
			+ "from \n"
			+ "(select (prd.location)endLocation, (prd.vehicle_reg_no)vehicleRegNo from public.processed_raw_data prd\n"
			+ "	inner join routedeviation rd on prd.vehicle_reg_no=rd.vehicleregno \n"
			+ "	where prd.string_date_time=rd.endtime)endLocation,\n"
			+ "(select (om.name)vltdName,(rom.route_long_name)routeName,(dem.dealer_name)dealerName,(pm.person_name)permitHolderName,(rlm.no_of_rto)rtoName,(dm.device_imei)deviceImei,(dm.device_iccid)deviceIccid,\n"
			+ "	(vm.vehicle_reg_no)vehicleRegNo,(atm.alert_name)alertType,(rd.starttime)startTime,(rd.endtime)endTime,(prd.location)startLocation,\n"
			+ "	(a.location)alertLocation,(prd.lat)locationLat,(prd.lon)locationLong from public.routedeviation rd\n"
			+ "	inner join route_master rom on rd.routeid=rom.id\n"
			+ "	inner join vehicle_master vm on rd.vehicleid=vm.id \n"
			+ "	inner join device_master dm on vm.device_id=dm.id\n"
			+ "	inner join oem_master om on dm.oem_id=om.id\n"
			+ "	inner join person_master pm on vm.person_id=pm.id\n"
			+ "	inner join dealer_master dem on vm.dealer_id=dem.id\n"
			+ "	inner join vehicle_device_mapping vdm on vm.id=vdm.vehicle_id\n"
			+ "	inner join rtolocation_master rlm on vdm.registering_rto_location_id=rlm.id\n"
			+ "	left join processed_raw_data prd on vm.vehicle_reg_no= prd.vehicle_reg_no and prd.vehicle_reg_no=rd.vehicleregno\n"
			+ "	left join alert a on vm.id=a.vehicle_id and prd.log_date=a.log_date and a.alert_time between rd.starttime::::time and rd.endtime::::time\n"
			+ "	left join alert_type_master atm on a.alert_type_id=atm.id\n"
			+ "	where prd.string_date_time=rd.starttime group by om.name,rom.route_long_name,dem.dealer_name,pm.person_name,\n"
			+ "	rlm.no_of_rto,dm.device_imei,dm.device_iccid,vm.vehicle_reg_no,atm.alert_name,rd.starttime,rd.endtime,prd.location,\n"
			+ "	a.location,prd.lat,prd.lon,prd.string_date_time)allother\n"
			+ "where allother.vehicleRegNo = endLocation.vehicleregno";
	
	public static final String GET_ROUTE_DEVIATION_REPORT_BY_FILTER="select distinct on(allother.startLocation,endLocation.endLocation) \n"
			+ "allother.vltdName,allother.vehicleRegNo,allother.routeName,allother.rtoName,allother.startTime,allother.endTime\n"
			+ "from \n"
			+ "(select (prd.location)endLocation, (prd.vehicle_reg_no)vehicleRegNo from public.processed_raw_data prd\n"
			+ "	inner join routedeviation rd on prd.vehicle_reg_no=rd.vehicleregno \n"
			+ "	where prd.string_date_time=rd.endtime)endLocation,\n"
			+ "(select (om.name)vltdName,(rom.route_long_name)routeName,(dem.dealer_name)dealerName,(pm.person_name)permitHolderName,(rlm.no_of_rto)rtoName,(dm.device_imei)deviceImei,(dm.device_iccid)deviceIccid,\n"
			+ "	(vm.vehicle_reg_no)vehicleRegNo,(atm.alert_name)alertType,(rd.starttime)startTime,(rd.endtime)endTime,(prd.location)startLocation,\n"
			+ "	(a.location)alertLocation,(prd.lat)locationLat,(prd.lon)locationLong from public.routedeviation rd\n"
			+ "	inner join route_master rom on rd.routeid=rom.id\n"
			+ "	inner join vehicle_master vm on rd.vehicleid=vm.id \n"
			+ "	inner join device_master dm on vm.device_id=dm.id\n"
			+ "	inner join oem_master om on dm.oem_id=om.id\n"
			+ "	inner join person_master pm on vm.person_id=pm.id\n"
			+ "	inner join dealer_master dem on vm.dealer_id=dem.id\n"
			+ "	inner join vehicle_device_mapping vdm on vm.id=vdm.vehicle_id\n"
			+ "	inner join rtolocation_master rlm on vdm.registering_rto_location_id=rlm.id\n"
			+ "	left join processed_raw_data prd on vm.vehicle_reg_no= prd.vehicle_reg_no and prd.vehicle_reg_no=rd.vehicleregno\n"
			+ "	left join alert a on vm.id=a.vehicle_id and prd.log_date=a.log_date and a.alert_time between rd.starttime::::time and rd.endtime::::time\n"
			+ "	left join alert_type_master atm on a.alert_type_id=atm.id\n"
			+ "	where prd.string_date_time=rd.starttime and rom.route_long_name=?1 and vm.vehicle_reg_no=?2 and rd.starttime >=cast(?3 as timestamp) and rd.starttime <=cast(?4 as timestamp)\n"
			+ "	group by om.name,rom.route_long_name,dem.dealer_name,pm.person_name,\n"
			+ "	rlm.no_of_rto,dm.device_imei,dm.device_iccid,vm.vehicle_reg_no,atm.alert_name,rd.starttime,rd.endtime,prd.location,\n"
			+ "	a.location,prd.lat,prd.lon,prd.string_date_time)allother\n"
			+ "where allother.vehicleRegNo = endLocation.vehicleregno";
	
	public static final String GET_SMS_ACKNOWLEDGEMENT_REPORT = "select om.name vltdName, \r\n"
			+ "dm.device_imei deviceImei,dm.device_iccid deviceIccid,\r\n" + "dlm.dealer_name dealerName,\r\n"
			+ "pm.primary_mobile_no mobileNo,\r\n"
			+ "cvs.version firmwareNo,cvs.recieved_date_time recievedDataTime,cvs.gps_status status,cvs.location location,cvs.lat smsLat,cvs.lon smsLon\r\n"
			+ "from vehicle_device_mapping vdm \r\n" + "inner join device_master dm on vdm.device_id=dm.id\r\n"
			+ "inner join vehicle_master vm on vdm.vehicle_id =vm.id \r\n"
			+ "inner join dealer_master dlm on vm.dealer_id=dlm.id \r\n"
			+ "inner join person_master pm on vm.person_id =pm.id \r\n"
			+ "inner join current_vehicle_string cvs on vm.id=cvs.vehicle_id \r\n"
			+ "inner join oem_master om on dm.oem_id =om.id group by\r\n" + " om.name, \r\n"
			+ "dm.device_imei,dm.device_iccid,\r\n" + "dlm.dealer_name,\r\n" + "pm.primary_mobile_no,\r\n"
			+ "cvs.version,cvs.recieved_date_time,cvs.gps_status,cvs.location,cvs.lat,cvs.lon\r\n" + "\r\n" + "";

	public static final String GET_SMS_ACKNOWLEDGEMENT_REPORT_BY_FILTER = "\r\n" + "select om.name vltdName, \r\n"
			+ "dm.device_imei deviceImei,dm.device_iccid deviceIccid,\r\n" + "dlm.dealer_name dealerName,\r\n"
			+ "pm.primary_mobile_no mobileNo,\r\n"
			+ "cvs.version firmwareNo,cvs.recieved_date_time recievedDataTime,cvs.gps_status status,cvs.location location,cvs.lat smsLat,cvs.lon smsLon\r\n"
			+ "from vehicle_device_mapping vdm \r\n" + "inner join device_master dm on vdm.device_id=dm.id\r\n"
			+ "inner join vehicle_master vm on vdm.vehicle_id =vm.id \r\n"
			+ "inner join dealer_master dlm on vm.dealer_id=dlm.id \r\n"
			+ "inner join person_master pm on vm.person_id =pm.id \r\n"
			+ "inner join current_vehicle_string cvs on vm.id=cvs.vehicle_id \r\n"
			+ "inner join oem_master om on dm.oem_id =om.id\r\n"
			+ "where om.name=?1 and cvs.recieved_date_time::::character varying BETWEEN ?2 AND ?3\r\n" + "group by\r\n"
			+ " om.name, \r\n" + "dm.device_imei,dm.device_iccid,\r\n" + "dlm.dealer_name,\r\n"
			+ "pm.primary_mobile_no,\r\n"
			+ "cvs.version,cvs.recieved_date_time,cvs.gps_status,cvs.location,cvs.lat,cvs.lon";
	
	public static final String GET_ALERT_SUMMARY_REPORT="select om.name vltdName,dlm.dealer_name dealerName, pm.person_name personName, rlm.no_of_rto rtoName, dm.device_imei deviceImei, dm.device_iccid deviceIccid,vm.vehicle_reg_no vehicleRegNo\r\n"
			+ ",atm.alert_name alertType,count(a.alert_type_id)alertCount,(a.log_date+max(a.alert_time))alertTime,a.location location,max(a.alert_lat)alertLat,max(a.alert_lon)alertLon\r\n"
			+ "from vehicle_device_mapping vdm \r\n"
			+ "inner join device_master dm on vdm.device_id =dm.id\r\n"
			+ "left join vehicle_master vm on vdm.vehicle_id =vm.id\r\n"
			+ "inner join oem_master om on dm.oem_id=om.id \r\n"
			+ "inner join person_master pm on vm.person_id =pm.id \r\n"
			+ "inner join rtolocation_master rlm on vdm.registering_rto_location_id = rlm.id \r\n"
			+ "inner join dealer_master dlm on vm.dealer_id = dlm.id \r\n"
			+ "left join alert a on dm.device_imei = a.imeino and vm.id=a.vehicle_id \r\n"
			+ "left join alert_type_master atm on a.alert_type_id =atm.id \r\n"
			+ "where atm.alert_analysis='Y'group by om.name ,dlm.dealer_name, pm.person_name, rlm.no_of_rto, dm.device_imei, dm.device_iccid,\r\n"
			+ "vm.vehicle_reg_no,atm.alert_name,a.location,a.log_date order by 10 desc";
	
	public static final String GET_ALERT_SUMMARY_REPORT_BY_FILTER="select om.name vltdName,dlm.dealer_name dealerName, pm.person_name personName, rlm.no_of_rto rtoName, dm.device_imei deviceImei, dm.device_iccid deviceIccid,vm.vehicle_reg_no vehicleRegNo\r\n"
			+ ",atm.alert_name alertType,count(a.alert_type_id)alertCount,max(a.alert_time)alertTime,a.location location,max(a.alert_lat)alertLat,max(a.alert_lon)alertLon\r\n"
			+ "from vehicle_device_mapping vdm \r\n"
			+ "inner join device_master dm on vdm.device_id =dm.id\r\n"
			+ "left join vehicle_master vm on vdm.vehicle_id =vm.id\r\n"
			+ "inner join oem_master om on dm.oem_id=om.id \r\n"
			+ "inner join person_master pm on vm.person_id =pm.id \r\n"
			+ "inner join rtolocation_master rlm on vdm.registering_rto_location_id = rlm.id \r\n"
			+ "inner join dealer_master dlm on vm.dealer_id = dlm.id \r\n"
			+ "left join alert a on dm.device_imei = a.imeino and vm.id=a.vehicle_id \r\n"
			+ "left join alert_type_master atm on a.alert_type_id =atm.id \r\n"
			+ "where atm.alert_analysis='Y'\r\n"
			+ "and om.name=?1 and vm.vehicle_reg_no=?2 and atm.alert_name=?3 and a.log_date::::character varying=?4\r\n"
			+ "group by om.name ,dlm.dealer_name, pm.person_name, rlm.no_of_rto, dm.device_imei, dm.device_iccid,\r\n"
			+ "vm.vehicle_reg_no,atm.alert_name,a.location ";
	
	public static final String GET_EMERGENCY_ALERT_REPORT_BY_FILTER="		 SELECT ALT.ID ID,\r\n"
			+ "			        OM.NAME VLTDNAME,\r\n"
			+ "			        DEM.DEALER_NAME DEALERNAME,\r\n"
			+ "			        PM.PERSON_NAME PERMITHOLDERDETAILS,\r\n"
			+ "					RM.NO_OF_RTO RTO,\r\n"
			+ "			        ALT.IMEINO DEVICEIMEI,\r\n"
			+ "					DM.DEVICE_UIN DEVICEUIN,\r\n"
			+ "					DM.DEVICE_ICCID DEVICEICCID,\r\n"
			+ "			        VM.VEHICLE_REG_NO VEHICLEREGNO,\r\n"
			+ "					ATM.ALERT_NAME ALERTTYPE,\r\n"
			+ "					ALT.SPEED ALERTSPEED,\r\n"
			+ "					ALT.ALERT_COMMENT MESSAGETYPE,\r\n"
			+ "			       concat(ALT.LOG_DATE,':',ALT.ALERT_TIME)  ALERTDATETIME,\r\n"
			+ "				   ALT.IS_ACTIVE  GPSSTATUS,\r\n"
			+ "				   ALT.LOCATION LOCATION,\r\n"
			+ "			        CONCAT(ALT.ALERT_LAT,\r\n"
			+ "			        ',',\r\n"
			+ "			        ALT.ALERT_LON) EMERGENCYLOC       \r\n"
			+ "			    FROM PUBLIC.ALERT ALT       \r\n"
			+ "			    LEFT JOIN PUBLIC.ALERT_TYPE_MASTER ATM ON ALT.ALERT_TYPE_ID = ATM.ID              \r\n"
			+ "			    LEFT JOIN PUBLIC.VEHICLE_DEVICE_MAPPING VDM ON ALT.VEHICLE_ID=VDM.VEHICLE_ID              \r\n"
			+ "			    LEFT JOIN PUBLIC.DEVICE_MASTER DM ON   DM.ID=ALT.DEVICE_ID       \r\n"
			+ "			    LEFT JOIN PUBLIC.OEM_MASTER OM ON DM.OEM_ID = OM.ID       \r\n"
			+ "			    LEFT JOIN PUBLIC.VEHICLE_MASTER VM  ON ALT.VEHICLE_ID = VM.ID       \r\n"
			+ "			    LEFT JOIN PUBLIC.RTOLOCATION_MASTER RM  ON VM.NODAL_AGENCY_ID = RM.ID       \r\n"
			+ "			    LEFT JOIN PUBLIC.DEALER_MASTER DEM ON VM.DEALER_ID =DEM.ID              \r\n"
			+ "			    LEFT JOIN PUBLIC.PERSON_MASTER PM  ON VM.PERSON_ID = PM.ID       \r\n"
			+ "			    WHERE   ALT.ALERT_TYPE_ID=12 AND OM.NAME=?1 AND VM.VEHICLE_REG_NO=?2\r\n"
			+ "				AND (concat(ALT.LOG_DATE,':',ALT.ALERT_TIME)::::character varying BETWEEN ?3 AND ?4)\r\n"
			+ "			    ORDER BY ID DESC ";
	
	public static final String GET_OVER_SPEED_ALERT_REPORT_BY_FILTER="select distinct on(a.alert_time) om.name vltdName,dem.dealer_name dealerName,pm.person_name permitHolderName,rlm.no_of_rto rtoName,dm.device_imei deviceImei,dm.device_iccid deviceIccid,\n"
			+ "vm.vehicle_reg_no vehicleRegNo,atm.alert_name alertType,(MIN((concat(a.log_date,':',a.alert_time))))startDateTime,(MAX((concat(a.log_date,':',a.alert_time))))endDateTime,(MAX(a.speed))speed,\n"
			+ "(cvs.ignition_status) ignitionStatus,(case when cvs.ignition_status=true then 'Running' when cvs.ignition_status=false then 'Stop' else 'Not Known' end)vehicleStatus,(cvs.gps_status)gpsStatus,concat(a.alert_lat , ',', a.alert_lon)overAlertLatlong,(a.location)alertLocation \n"
			+ "from alert a \n"
			+ "left join alert_type_master atm on a.alert_type_id =atm.id \n"
			+ "left join device_master dm on a.imeino =dm.device_imei \n"
			+ "left join vehicle_master vm on dm.id=vm.device_id \n"
			+ "left join vehicle_device_mapping vdm on vm.id=vdm.vehicle_id  and dm.id=vdm.device_id \n"
			+ "left join dealer_master dem on vm.dealer_id =dem.id \n"
			+ "left join oem_master om on dm.oem_id =om.id \n"
			+ "left join person_master pm on vm.person_id =pm.id \n"
			+ "left join current_vehicle_string cvs on a.imeino =cvs.imei_no and vm.id=cvs.vehicle_id and dm.device_imei =cvs.imei_no \n"
			+ "left join rtolocation_master rlm on vdm.registering_rto_location_id  = rlm.id \n"
			+ "where a.vehicle_id = vdm.vehicle_id and a.alert_type_id = 4 and om.name=?1 and vm.vehicle_reg_no =?2 and (concat(a.log_date,':',a.alert_time))::::timestamp between cast(?3 as timestamp) and cast(?4 as timestamp) \r\n"
			+ "group by om.name ,dem.dealer_name ,pm.person_name ,rlm.no_of_rto ,dm.device_imei ,dm.device_iccid,\n"
			+ "vm.vehicle_reg_no,atm.alert_name,cvs.ignition_status,cvs.gps_status,a.location,a.alert_lat,a.alert_lon,a.alert_time";

	public static final String GET_SMS_REQUEST_REPORT="select \r\n"
			+ "om.name vltdName,\r\n"
			+ "rm.no_of_rto rtoName,\r\n"
			+ "dm.device_imei deviceImei,\r\n"
			+ "dm.device_iccid deviceIccid,\r\n"
			+ "vm.vehicle_reg_no vehicleRegNo,\r\n"
			+ "'Message'  message, \r\n"
			+ "pm.primary_mobile_no mobile1,\r\n"
			+ "pm.secondary_mobile_no mobile2,\r\n"
			+ "'Request Proceeded'  response, \r\n"
			+ "(TO_CHAR(vm.created_date, 'DD-MM-YYYY HH:MM:SS')) responseDateTime,\r\n"
			+ "'Delivered' deliveryStatus \r\n"
			+ "from \r\n"
			+ "vehicle_master vm \r\n"
			+ "inner join vehicle_device_mapping vdm on vm.id=vdm.vehicle_id \r\n"
			+ "inner join rtolocation_master rm on vdm.registering_rto_location_id =rm.id\r\n"
			+ "inner join device_master dm  on vm.device_id = dm.id \r\n"
			+ "inner join oem_master om on dm.oem_id =om.id \r\n"
			+ "inner join permitholder_master tpm on vm.id = tpm.vehicle_id\r\n"
			+ "inner join person_master pm on tpm.person_id = pm.id\r\n"
			+ "";
	
	public static final String GET_SMS_REQUEST_REPORT_BY_FILTER="select \r\n"
			+ "om.name vltdName,\r\n"
			+ "rm.no_of_rto rtoName,\r\n"
			+ "dm.device_imei deviceImei,\r\n"
			+ "dm.device_iccid deviceIccid,\r\n"
			+ "vm.vehicle_reg_no vehicleRegNo,\r\n"
			+ "'Message'  message, \r\n"
			+ "pm.primary_mobile_no mobile1,\r\n"
			+ "pm.secondary_mobile_no mobile2,\r\n"
			+ "'Request Proceeded'  response, \r\n"
			+ "(TO_CHAR(vm.created_date, 'DD-MM-YYYY HH:MM:SS')) responseDateTime,\r\n"
			+ "'Delivered' deliveryStatus from \r\n"
			+ "vehicle_master vm \r\n"
			+ "inner join vehicle_device_mapping vdm on vm.id=vdm.vehicle_id \r\n"
			+ "inner join rtolocation_master rm on vdm.registering_rto_location_id =rm.id\r\n"
			+ "inner join device_master dm  on vm.device_id = dm.id \r\n"
			+ "inner join oem_master om on dm.oem_id =om.id \r\n"
			+ "inner join permitholder_master tpm on vm.id = tpm.vehicle_id\r\n"
			+ "inner join person_master pm on tpm.person_id = pm.id\r\n"
			+ "where om.name=?1 and vm.vehicle_reg_no =?2 and (TO_CHAR(vm.created_date, 'DD-MM-YYYY HH:MM:SS'))::::character varying between ?3 and ?4";
	
	public static final String GET_ALERT_REPORT_BY_FILTER="  select  atm.alert_name alertName,sum(alertCount)as alertCount from \r\n"
			+ "\r\n"
			+ "  (select distinct alert_name,id,alert_analysis  from alert_type_master where alert_analysis ='Y') atm \r\n"
			+ "\r\n"
			+ "inner join  LATERAL (\r\n"
			+ "    select  count(a.alert_type_id)alertCount from alert a\r\n"
			+ "--inner join alert_type_master atm  on a.alert_type_id=atm.id \r\n"
			+ "inner join device_master dm on a.device_id =dm.id \r\n"
			+ "inner join oem_master om on dm.oem_id =om.id\r\n"
			+ "inner join vehicle_device_mapping vdm on vdm.device_id =dm.id \r\n"
			+ "inner join vehicle_master vm on vdm.vehicle_id =vm.id --) AS b group by atm.alert_name;\r\n"
			+"where atm.alert_analysis ='Y' and a.alert_type_id = atm.id and om.name=?1 and vm.vehicle_reg_no=?2 and atm.alert_name=?3 and a.log_date=?4) AS b on true group by atm.alert_name";
	
	
	public static final String GET_ALERT_REPORT_BY_FILTER_NO_ALERT_NAME="  select  atm.alert_name alertName,sum(alertCount)as alertCount from \r\n"
			+ "\r\n"
			+ "  (select distinct alert_name,id,alert_analysis  from alert_type_master where alert_analysis ='Y') atm \r\n"
			+ "\r\n"
			+ "inner join  LATERAL (\r\n"
			+ "    select  count(a.alert_type_id)alertCount from alert a\r\n"
			+ "--inner join alert_type_master atm  on a.alert_type_id=atm.id \r\n"
			+ "inner join device_master dm on a.device_id =dm.id \r\n"
			+ "inner join oem_master om on dm.oem_id =om.id\r\n"
			+ "inner join vehicle_device_mapping vdm on vdm.device_id =dm.id \r\n"
			+ "inner join vehicle_master vm on vdm.vehicle_id =vm.id --) AS b group by atm.alert_name;\r\n"
			+ " where a.alert_type_id=atm.id and a.alert_type_id = atm.id and atm.alert_analysis ='Y' and om.name=?1 and vm.vehicle_reg_no=?2 \r\n"
			+ " and a.log_date=?3 ) AS b on true group by atm.alert_name";
	
	public static final String GET_GEOFENCE_IN_OUT_REPORT="select om.name vltdName, dem.dealer_name dealerName, pm.person_name permitHolderName, dm.device_imei deviceImei, dm.device_iccid deviceIccid, vm.vehicle_reg_no vehicleRegNo, a.log_date alertDate, gs.geofence_in_lat inLat, gs.geofence_in_lon inLon, gs.geofence_out_lat outLat, gs.geofence_out_lon outLon, gs.geofence_in_time inTime, gs.geofence_out_time outTime, pom.description poiName, gs.in_distance inDistance, gs.out_distance outDistance,\n"
			+ "(case when gs.geo_status=true then 'In' else 'Out' end) status\n"
			+ "from geofence_summary gs \n"
			+ "inner join vehicle_master vm on vm.id = gs.vehicle_id\n"
			+ "inner join device_master dm on dm.id = vm.device_id\n"
			+ "inner join vehicle_device_mapping vdm on vdm.device_id =dm.id and vdm.vehicle_id =vm.id \n"
			+ "inner join oem_master om on om.id = dm.oem_id\n"
			+ "inner join person_master pm on pm.id = vm.person_id\n"
			+ "inner join dealer_master dem on dem.id = vm.dealer_id\n"
			+ "left join alert a on a.imeino = dm.device_imei and a.log_date =gs.geofence_in_time::::date \n"
			+ "inner join poi_master pom on pom.id = gs.geofence_id";
	
	public static final String GET_GEOFENCE_IN_OUT_REPORT_BY_FILTER="select om.name vltdName, dem.dealer_name dealerName, pm.person_name permitHolderName, dm.device_imei deviceImei, dm.device_iccid deviceIccid, vm.vehicle_reg_no vehicleRegNo, a.log_date alertDate, gs.geofence_in_lat inLat, gs.geofence_in_lon inLon, gs.geofence_out_lat outLat, gs.geofence_out_lon outLon, gs.geofence_in_time inTime, gs.geofence_out_time outTime, pom.description poiName, gs.in_distance inDistance, gs.out_distance outDistance,\n"
			+ "(case when gs.geo_status=true then 'In' else 'Out' end) status\n"
			+ "from geofence_summary gs \n"
			+ "inner join vehicle_master vm on vm.id = gs.vehicle_id\n"
			+ "inner join device_master dm on dm.id = vm.device_id\n"
			+ "inner join vehicle_device_mapping vdm on vdm.device_id =dm.id and vdm.vehicle_id =vm.id \n"
			+ "inner join oem_master om on om.id = dm.oem_id\n"
			+ "inner join person_master pm on pm.id = vm.person_id\n"
			+ "inner join dealer_master dem on dem.id = vm.dealer_id\n"
			+ "left join alert a on a.imeino = dm.device_imei and a.log_date =gs.geofence_in_time::::date \n"
			+ "inner join poi_master pom on pom.id = gs.geofence_id\n"
			+ "where om.name=?1 and vm.vehicle_reg_no=?2 and gs.geofence_in_time::::date between cast(?3 as date) and cast(?4 as date)";
} 
