package com.trinetra.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.master.DealerMaster;
import com.trinetra.model.page.response.AlertTypeCounts;
import com.trinetra.model.page.response.GetNonPollingAndPendingAlert;

@Repository
public interface AnalyticsRepo extends JpaRepository<DealerMaster, Long>{

	
	@Query(value = "select dpa.id, dpa.port_number from device_port_assignment dpa", nativeQuery = true)
	public List<Object[]> getListOfPorts();
	
	@Query(value="select  atm.alert_name alertName,sum(alertCount)alertCount from \r\n"
			+ "(select distinct alert_name,id,support_ui from alert_type_master where support_ui='Y' ) atm \r\n"
			+ "inner join  LATERAL (\r\n"
			+ "    select  count(a.alert_type_id)alertCount from alert a\r\n"
			+ "--inner join alert_type_master atm  on a.alert_type_id=atm.id \r\n"
			+ "inner join device_master dm on a.device_id =dm.id \r\n"
			+ "inner join oem_master om on dm.oem_id =om.id\r\n"
			+ "inner join address_master am on om.address_id =am.id \r\n"
			+ "inner join pincode_master pm on am.pincode_id =pm.id\r\n"
			+ "inner join vehicle_device_mapping vdm on vdm.device_id =dm.id \r\n"
			+ "inner join rtolocation_master rm on vdm.registering_rto_location_id =rm.id\r\n"
			+ "inner join state_master sm on pm.state_id =sm.id\r\n"
			+ "inner join vehicle_master vm on vdm.vehicle_id =vm.id \r\n"
			+ "where a.alert_type_id=atm.id and atm.support_ui='Y' and a.log_date =current_date \r\n"
			+ "and sm.id=?1 and rm.id=?2) AS b on true group by atm.alert_name,atm.support_ui",nativeQuery = true)
	public List<AlertTypeCounts> getAlertCountsByStateAndRto(int stateId, int rtoId);
	
	@Query(value="select nonPolling.nonPolling,pendingAlert.pendingAlert from\r\n"
			+ "\r\n"
			+ "(select ((select count(*)totalCount from vehicle_device_mapping vdm \r\n"
			+ "inner join vehicle_master vm on vdm.vehicle_id =vm.id)-\r\n"
			+ "(select count(*) liveCount from vehicle_device_mapping vdm \r\n"
			+ "inner join device_master dm on vdm.device_id =dm.id \r\n"
			+ "inner join oem_master om on dm.oem_id =om.id\r\n"
			+ "inner join address_master am on om.address_id =am.id \r\n"
			+ "inner join pincode_master pm on am.pincode_id =pm.id\r\n"
			+ "inner join rtolocation_master rm on vdm.registering_rto_location_id =rm.id\r\n"
			+ "inner join state_master sm on pm.state_id =sm.id\r\n"
			+ "inner join current_vehicle_string cvs on cvs.vehicle_id =vdm.vehicle_id \r\n"
			+ " inner join vehicle_master vm on cvs.vehicle_id=vm.id and vdm.vehicle_id =vm.id and cvs.string_date_time::::date =current_date \r\n"
			+ "and sm.id=:stateId and rm.id=:rtoId))nonPolling)nonPolling,\r\n"
			+ "\r\n"
			+ "(select (select count(*) from (\r\n"
			+ "select dm.device_imei, row_number() over (partition by a.imeino order by  a.alert_time desc)\r\n"
			+ "from alert a \r\n"
			+ "inner join vehicle_master vm on vm.id = a.vehicle_id\r\n"
			+ "inner join vehicle_device_mapping vdm on vdm.vehicle_id =vm.id\r\n"
			+ "inner join device_master dm on dm.id = a.device_id\r\n"
			+ "inner join oem_master om on om.id = dm.oem_id\r\n"
			+ "inner join address_master am on om.address_id =am.id \r\n"
			+ "inner join pincode_master pm1 on am.pincode_id =pm1.id\r\n"
			+ "inner join rtolocation_master rm on vdm.registering_rto_location_id =rm.id\r\n"
			+ "inner join state_master sm on pm1.state_id =sm.id\r\n"
			+ "inner join current_vehicle_string cvs on cvs.imei_no = a.imeino\r\n"
			+ "where a.alert_type_id = 12 and a.log_date = current_date \r\n"
			+ "and sm.id=:stateId and rm.id=:rtoId\r\n"
			+ ")th where row_number=1)-(select Count(*) from emergency_resolve er where er.resolved_time::::date=current_date)pendingAlert)pendingAlert\r\n",nativeQuery = true)
	public GetNonPollingAndPendingAlert getNonPollingAndPendingAlert(@Param("stateId") int stateId,@Param("rtoId") int rtoId);
	
}
