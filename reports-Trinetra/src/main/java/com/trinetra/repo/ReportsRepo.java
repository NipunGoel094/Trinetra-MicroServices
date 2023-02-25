package com.trinetra.repo;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinetra.entity.query.EventManagementAlertQuery;
import com.trinetra.entity.query.ReportsQuery;
import com.trinetra.model.entity.User;
import com.trinetra.model.page.request.EmergencyResolveRequest;
import com.trinetra.model.page.response.AlertSummaryReportResponse;
import com.trinetra.model.page.response.AlertTypeCounts;
import com.trinetra.model.page.response.EmergencyAlertVoReportResponce;
import com.trinetra.model.page.response.GeofenceInOutReportResponse;
import com.trinetra.model.page.response.GetAccountBalanceReportResponse;
import com.trinetra.model.page.response.SMS_Request_Report_Response;

@Repository
public interface ReportsRepo extends JpaRepository<User, Integer>{

	@Transactional
	@Modifying
	@Query(value = "insert into emergency_resolve(device_id, alert_type, alert_generation_time, resolved_time, resolver_mobile_no, location, latitude, longitude, action, remarks)\n"
			+ "values(:#{#err.deviceId},:#{#err.alertType},:#{#err.alertGenerationTime},:#{#err.resolvedTime},:#{#err.resolverMobileNo},:#{#err.location},:#{#err.latitude},:#{#err.longitude},:#{#err.action},:#{#err.remarks})", nativeQuery = true)
	public void insertInEmergencyResolve(@Param("err") EmergencyResolveRequest err);

	@Query(value = ReportsQuery.GET_ALERT_SUMMARY_REPORT, nativeQuery = true)
	public Page<AlertSummaryReportResponse> getAlertSummaryReport(Pageable pageable);

	@Query(value = ReportsQuery.GET_ALERT_SUMMARY_REPORT_BY_FILTER, nativeQuery = true)
	public Page<AlertSummaryReportResponse> getAlertSummaryDetailsByFilter(String vltdName, String vehicleRegNo,
			String alertName, String alertDate, Pageable pageable);
	
	@Query(value ="SELECT\r\n"
			+ "        ALT.ID ID,\r\n"
			+ "        OM.NAME VLTDNAME,\r\n"
			+ "        DEM.DEALER_NAME DEALERNAME,\r\n"
			+ "        PM.PERSON_NAME PERMITHOLDERDETAILS,\r\n"
			+ "		RM.NO_OF_RTO RTO,\r\n"
			+ "        ALT.IMEINO DEVICEIMEI,\r\n"
			+ "			DM.ID DEVICEID,\r\n"
			+ "		DM.DEVICE_UIN DEVICEUIN,\r\n"
			+ "		DM.DEVICE_ICCID DEVICEICCID,\r\n"
			+ "        VM.VEHICLE_REG_NO VEHICLEREGNO,\r\n"
			+ "		ATM.ALERT_NAME ALERTTYPE,\r\n"
			+ "		ALT.SPEED ALERTSPEED,\r\n"
			+ "		ALT.ALERT_COMMENT MESSAGETYPE,\r\n"
			+ "       concat(ALT.LOG_DATE,':',ALT.ALERT_TIME)  ALERTDATETIME,\r\n"
			+ "	   ALT.IS_ACTIVE  GPSSTATUS,\r\n"
			+ "	   ALT.LOCATION LOCATION,\r\n"
			+ "        CONCAT(ALT.ALERT_LAT,\r\n"
			+ "        ',',\r\n"
			+ "        ALT.ALERT_LON) EMERGENCYLOC       \r\n"
			+ "    FROM\r\n"
			+ "        PUBLIC.ALERT ALT       \r\n"
			+ "    LEFT JOIN\r\n"
			+ "        PUBLIC.ALERT_TYPE_MASTER ATM               \r\n"
			+ "            ON       ALT.ALERT_TYPE_ID = ATM.ID              \r\n"
			+ "    LEFT JOIN\r\n"
			+ "        PUBLIC.VEHICLE_DEVICE_MAPPING VDM               \r\n"
			+ "            ON ALT.VEHICLE_ID=VDM.VEHICLE_ID              \r\n"
			+ "    LEFT JOIN\r\n"
			+ "        PUBLIC.DEVICE_MASTER DM               \r\n"
			+ "            ON   DM.ID=ALT.DEVICE_ID       \r\n"
			+ "    LEFT JOIN\r\n"
			+ "        PUBLIC.OEM_MASTER OM               \r\n"
			+ "            ON DM.OEM_ID = OM.ID       \r\n"
			+ "    LEFT JOIN\r\n"
			+ "        PUBLIC.VEHICLE_MASTER VM               \r\n"
			+ "            ON ALT.VEHICLE_ID = VM.ID       \r\n"
			+ "    LEFT       JOIN\r\n"
			+ "        PUBLIC.RTOLOCATION_MASTER RM               \r\n"
			+ "            ON VM.NODAL_AGENCY_ID = RM.ID       \r\n"
			+ "    LEFT JOIN\r\n"
			+ "        PUBLIC.DEALER_MASTER DEM               \r\n"
			+ "            ON VM.DEALER_ID =DEM.ID              \r\n"
			+ "    LEFT JOIN\r\n"
			+ "        PUBLIC.PERSON_MASTER PM               \r\n"
			+ "            ON VM.PERSON_ID = PM.ID       \r\n"
			+ "    WHERE\r\n"
			+ "        ALT.ALERT_TYPE_ID=12  \r\n"
			+ "           \r\n"
			+ "    ORDER BY\r\n"
			+ "        ID DESC ",nativeQuery = true)
	public Page<EmergencyAlertVoReportResponce> findEmergengyAlertsReport(Pageable pageable);

	@Query(value = ReportsQuery.GET_EMERGENCY_ALERT_REPORT_BY_FILTER, nativeQuery = true)
	public Page<EmergencyAlertVoReportResponce> getEmergencyAlertsDetailsByFilter(String vltdName, String vehicleRegNo,
			String startDateTime, String endDateTime, Pageable pageable);

	@Query(value = ReportsQuery.GET_GEOFENCE_IN_OUT_REPORT, nativeQuery = true)
	public Page<GeofenceInOutReportResponse> getGeofenceInOutReport(Pageable pageable);

	@Query(value = ReportsQuery.GET_GEOFENCE_IN_OUT_REPORT_BY_FILTER, nativeQuery = true)
	public Page<GeofenceInOutReportResponse> getGeofenceInOutReportByFilter(String vltdName, String vehicleRegNo,
			String startDate, String endDate, Pageable pageable);
	
	@Query(value=ReportsQuery.GET_SMS_REQUEST_REPORT,nativeQuery = true)
	public Page<SMS_Request_Report_Response> getSMSRequestReport(Pageable pageable);

	@Query(value=ReportsQuery.GET_SMS_REQUEST_REPORT_BY_FILTER,nativeQuery = true)
	public Page<SMS_Request_Report_Response> getSMSRequestDetailsByFilter(String vltdName, String vehicleRegNo,
			String startDateTime, String endDateTime, Pageable pageable);
	
	@Query(value = EventManagementAlertQuery.GET_ALERT_TYPE_COUNTS, nativeQuery = true)
	public List<AlertTypeCounts> getAlertTypeCounts();
	
	@Query(value=ReportsQuery.GET_ALERT_REPORT_BY_FILTER,nativeQuery = true)
	public List<AlertTypeCounts> getAlertTypeCountsByFilter(String vltdName, String vehicleRegNo, String alertName,
			Date date);
	
	@Query(value=ReportsQuery.GET_ALERT_REPORT_BY_FILTER_NO_ALERT_NAME,nativeQuery = true)
	public List<AlertTypeCounts> getAlertTypeByFilter( String vltdName,String vehicleRegNo, Date date);
	
	@Query(value="select om.name vltdName,to_char(ar.payment_date, 'month') AS Month,count(dm.id)as deviceCount,ar.amount amount  from account_recharge ar \r\n"
			+ "			  	 	inner join oem_master om on ar.oem_id =om.id\r\n"
			+ "			  	 	inner join device_master dm on dm.oem_id =om.id \r\n"
			+ "			  	 	inner join vehicle_device_mapping vdm  on vdm.device_id=dm.id\r\n"
			+ " where om.name=:vltdName and ar.payment_date::::character varying  between :startDate and :endDate \r\n"
			+ "			  	 	group by om.name,ar.payment_date,ar.amount",nativeQuery = true)
	Page<GetAccountBalanceReportResponse> getAccountBalanceReportByFilter(Pageable pageable,@Param("vltdName") String vltdName,
			@Param("startDate") String startDate,@Param("endDate") String endDate);
	
	@Query(value="select om.name vltdName,dm2.dealer_name dealerName,pm.person_name personName, vm.vehicle_reg_no vehicleRegNo ,dm.device_imei deviceImei,dm.device_iccid deviceIccid ,tm.description vehicleType ,ar.amount amount , to_char((ar.payment_date),'dd/MM/yyyy hh:mm:ss')::::character varying as tranactionDate \r\n"
			+ " from account_recharge ar \r\n"
			+ "inner join oem_master om on ar.oem_id=om.id \r\n"
			+ "inner join device_master dm on dm.oem_id =om.id\r\n"
			+ "inner join vehicle_master vm on vm.device_id =dm.id\r\n"
			+ "inner join type_master tm on vm.vehicle_type_id =tm.id\r\n"
			+ "inner join permitholder_master phm on phm.vehicle_id=vm.id\r\n"
			+ "inner join person_master pm on phm.person_id=pm.id\r\n"
			+ "inner join vehicle_device_mapping vdm on vdm.device_id =dm.id and vdm.vehicle_id =vm.id\r\n"
			+ "inner join dealer_master dm2 on dm2.oem_id =om.id\r\n"
			+ "inner join device_dealer_mapping ddm on ddm.dealer_id=dm2.id \r\n"
			+ "where om.name=:vltdName and ar.payment_date::::character varying  between :startDate and :endDate \r\n"
			+ "group by om.name,dm2.dealer_name ,pm.person_name, vm.vehicle_reg_no ,dm.device_imei ,dm.device_iccid ,tm.description ,ar.amount ,ar.payment_date",nativeQuery = true)
	Page<Object[]> getDeviceTransactionDetails(Pageable pageable, @Param("vltdName") String vltdName,
			@Param("startDate") String startDate,@Param("endDate") String endDate);
	
	@Query(value="select om.name vltdName,ar.amount amount ,to_char((ar.recharge_date),'dd/MM/yyyy hh:mm:ss')::::character varying  rechargeDate \r\n"
			+ " from account_recharge ar \r\n"
			+ "inner join oem_master om on ar.oem_id=om.id \r\n"
			+ "inner join device_master dm on dm.oem_id =om.id\r\n"
			+ "inner join vehicle_device_mapping vdm on vdm.device_id =dm.id \r\n"
			+ "where om.name=:vltdName and ar.recharge_date::::character varying  between :startDate and :endDate \r\n"
			+ "group by om.name,ar.amount ,ar.recharge_date",nativeQuery = true)
	Page<Object[]> getVltdRechargeDetails(Pageable pageable, @Param("vltdName") String vltdName,
			@Param("startDate") String startDate,@Param("endDate") String endDate);
}
