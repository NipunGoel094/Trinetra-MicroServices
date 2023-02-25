package com.trinetra.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trinetra.model.entity.ConnectedDeviceDetails;
import com.trinetra.model.page.response.ConnectedDevicesDetailsResponse;

public interface ConnectedDeviceDetailsRepo extends JpaRepository<ConnectedDeviceDetails, Integer> {
	
	@Query(value = "select * from view_connected_device_details\n"
			+ "where vehicle_chassis_no ilike %:text% or vehicle_reg_no ilike %:text% order by polling_time desc", nativeQuery = true)
	public Page<ConnectedDeviceDetails> getConnectedDevicesByChassisNo(@Param("text") String text, Pageable pageable);
	
	@Query(value = "select * from view_connected_device_details\n"
			+ "where polling_time >= now() - :pollingHour * interval '1 hour' and (polling_time::::date =current_date or polling_time::::date =current_date - 1) order by polling_time desc", nativeQuery = true)
	public Page<ConnectedDeviceDetails> getConnectedDevicesByPollingHour(@Param("pollingHour") int pollingHour, Pageable pageable);
	
	@Query(value = "select * from view_connected_device_details\n"
			+ "where polling_time::::date = current_date order by polling_time desc", nativeQuery = true)
	public Page<ConnectedDeviceDetails> getConnectedDevicesByPollingForToday(Pageable pageable);
	
	@Query(value = "select dpa.port_number portNo, vm.chassis_no vehicleChassisNo, cvs.string_date_time pollingTime, cvs.lat lat, cvs.lon lon, null clientIP, null networkProviderIP, cvs.mobile_country_code mobileCountryCode, cvs.mobile_network_code mobileNetworkCode, \n"
			+ "cvs.local_area_code localAreaCode, cvs.cell_id cellID, null nMRDetails\n"
			+ "from current_vehicle_string cvs \n"
			+ "inner join vehicle_master vm on cast(vm.id as varchar) = cast(cvs.vehicle_id as varchar)\n"
			+ "inner join device_master dm on cast(dm.id as varchar) = cast(cvs.device_id as varchar)\n"
			+ "inner join oem_master om on om.id = dm.oem_id\n"
			+ "inner join device_port_assignment dpa on dpa.oem_id = om.id where dpa.id = :id", nativeQuery = true)
	public List<ConnectedDevicesDetailsResponse> getConnectedDevicesByPortId(@Param("id") int portId);
	
	@Query(value = "select dpa.id, dpa.port_number from device_port_assignment dpa", nativeQuery = true)
	public List<Object[]> getListOfPorts();
}
