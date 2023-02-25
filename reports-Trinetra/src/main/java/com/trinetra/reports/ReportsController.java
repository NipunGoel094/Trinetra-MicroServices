package com.trinetra.reports;
import static com.trinetra.model.enums.Status.SUCCESS;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trinetra.exception.CustomException;
import com.trinetra.model.entity.AlertSummaryReport;
import com.trinetra.model.entity.EmergencyResolvedAlerts;
import com.trinetra.model.entity.GeofenceInOutReport;
import com.trinetra.model.entity.HistoryLogReport;
import com.trinetra.model.entity.OverSpeedAlert;
import com.trinetra.model.entity.RouteDeviationReport;
import com.trinetra.model.entity.SmsAcknowledgementReport;
import com.trinetra.model.page.request.EmergencyResolveRequest;
import com.trinetra.model.page.response.AccountSummeryReportByDevice;
import com.trinetra.model.page.response.AlertSummaryReportResponse;
import com.trinetra.model.page.response.AlertTypeCounts;
import com.trinetra.model.page.response.EmergencyAlertVoReportResponce;
import com.trinetra.model.page.response.GeofenceInOutReportResponse;
import com.trinetra.model.page.response.GetAccountBalanceReportResponse;
import com.trinetra.model.page.response.SMS_Request_Report_Response;
import com.trinetra.model.wrapper.ResponseWrapper;
import com.trinetra.service.ReportsService;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportsController {

	@Autowired
	private ReportsService reportsService;

	@GetMapping("/getEmergencyResolvedAlertReport")
	public ResponseEntity<ResponseWrapper> getEmergencyResolvedAlertReport(@RequestParam("page") int page, @RequestParam("size") int size) {
		Page<EmergencyResolvedAlerts> emergencyResolvedAlertReport=reportsService.getEmergencyResolvedAlertReport(page,size);
		return new ResponseEntity<ResponseWrapper> (new ResponseWrapper(SUCCESS.name, emergencyResolvedAlertReport), HttpStatus.OK);
	}
	
	@GetMapping("/getEmergencyResolvedAlertReportByFilter")
	public ResponseEntity<ResponseWrapper> getEmergencyResolvedAlertReportByFilter(@RequestParam("vltdName")String vltdName,@RequestParam("vehicleRegNo")String vehicleRegNo,@RequestParam("fromDate")String fromDate,@RequestParam("toDate")String toDate, @RequestParam("page") int page, @RequestParam("size") int size) {
		Page<EmergencyResolvedAlerts> emergencyResolvedAlertReport=reportsService.getEmergencyResolvedAlertReportByFilter(vltdName,vehicleRegNo,fromDate,toDate,page,size);
		return new ResponseEntity<ResponseWrapper> (new ResponseWrapper(SUCCESS.name, emergencyResolvedAlertReport), HttpStatus.OK);
	}
	
	@GetMapping("/getHistoryLogReport")
	public ResponseEntity<ResponseWrapper> getHistoryLogReport(@RequestParam("vltdName")String vltdName,@RequestParam("vehicleRegNo")String vehicleRegNo,@RequestParam("startDateTime")String startDateTime,@RequestParam("endDateTime")String endDateTime,@RequestParam("page")int page,@RequestParam("size")int size){
		Page<HistoryLogReport> historyLogReport=reportsService.getHistoryLogReport(vltdName,vehicleRegNo,startDateTime,endDateTime,page,size);
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name,historyLogReport), HttpStatus.OK);
		
	}
	
	@GetMapping("/getAllHistoryLogReport")
	public ResponseEntity<ResponseWrapper> getAllHistoryLogReport(@RequestParam("page") int page, @RequestParam("size") int size){
		Page<HistoryLogReport> historyLogReport=reportsService.getAllHistoryLogReport(page,size);
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name,historyLogReport), HttpStatus.OK);
		
	}
	
	@GetMapping("/SearchByHistoryLogReport")
	public ResponseEntity<ResponseWrapper> searchByHistoryLogReport(@RequestParam("input") String input,@RequestParam("page") int page, @RequestParam("size") int size){
		Page<HistoryLogReport> historyLogReport=reportsService.searchByHistoryLogReport(input,page,size);
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name,historyLogReport), HttpStatus.OK);
		
	}
	
	@GetMapping("/getOverSpeedAlertReport")
	public ResponseEntity<ResponseWrapper> getOverSpeedAlertReport(@RequestParam("page")int page,@RequestParam("size")int size){
		Page<OverSpeedAlert> overSpeedReport=reportsService.getOverSpeedAlertReport(page,size);
		return new ResponseEntity<ResponseWrapper> (new ResponseWrapper(SUCCESS.name, overSpeedReport), HttpStatus.OK);
		
	}
	
	@GetMapping("/getOverSpeedAlertDetailsByFilter")
	public ResponseEntity<ResponseWrapper> getOverSpeedAlertDetailsByFilter(@RequestParam("vltdName")String vltdName,@RequestParam("vehicleRegNo")String vehicleRegNo,@RequestParam("startDateTime")String startDateTime,@RequestParam("endDateTime")String endDateTime,@RequestParam("page")int page,@RequestParam("size")int size){
		Page<OverSpeedAlert> overSpeedReport=reportsService.getOverSpeedAlertDetailsByFilter(vltdName, vehicleRegNo,startDateTime,endDateTime,page,size);
		return new ResponseEntity<ResponseWrapper> (new ResponseWrapper(SUCCESS.name, overSpeedReport), HttpStatus.OK);
		
	}
	
	@GetMapping("/getRouteDeviationReport")
	public ResponseEntity<ResponseWrapper> getRouteDeviationReport(@RequestParam("page")int page,@RequestParam("size")int size){
		Page<RouteDeviationReport> routeDeviationReport=reportsService.getRouteDeviationReport(page,size);
		return new ResponseEntity<ResponseWrapper> (new ResponseWrapper(SUCCESS.name, routeDeviationReport), HttpStatus.OK);
		
	}
	
	@GetMapping("/getRouteDeviationDetailsByFilter")
	public ResponseEntity<ResponseWrapper> getRouteDeviationDetailsByFilter(@RequestParam("routeName")String routeName,@RequestParam("vehicleRegNo")String vehicleRegNo,@RequestParam("startDateTime")String startDateTime,@RequestParam("endDateTime")String endDateTime,@RequestParam("page")int page,@RequestParam("size")int size){
		Page<RouteDeviationReport> routeDeviation=reportsService.getRouteDeviationDetailsByFilter(routeName,vehicleRegNo,startDateTime,endDateTime,page,size);
		return new ResponseEntity<ResponseWrapper> (new ResponseWrapper(SUCCESS.name, routeDeviation), HttpStatus.OK);
		
	}
	
	@PostMapping("/saveEmergencyResolution")
	public ResponseEntity<ResponseWrapper> saveEmergencyResolution(@RequestBody EmergencyResolveRequest emergencyResolveRequest){
		String result = reportsService.saveEmergencyResolved(emergencyResolveRequest);
		return new ResponseEntity<ResponseWrapper> (new ResponseWrapper(SUCCESS.name, result), HttpStatus.OK);
		
	}
	
	@GetMapping("/getSmsAcknowledgementReport")
	public ResponseEntity<ResponseWrapper> getSmsAcknowledgementReport(@RequestParam("page")int page,@RequestParam("size")int size){
		Page<SmsAcknowledgementReport> smsReport=reportsService.getSmsAcknowledgementReport(page,size);
		return new ResponseEntity<ResponseWrapper> (new ResponseWrapper(SUCCESS.name, smsReport), HttpStatus.OK);
	}
	@GetMapping("/getSmsAcknowledgementDetailsByFilter")
	public ResponseEntity<ResponseWrapper> getSmsAcknowledgementDetailsByFilter(@RequestParam("vltdName")String vltdName,@RequestParam("startDateTime")String startDateTime,@RequestParam("endDateTime")String endDateTime,@RequestParam("page")int page,@RequestParam("size")int size){
		Page<SmsAcknowledgementReport> smsReport=reportsService.getSmsAcknowledgementDetailsByFilter(vltdName,startDateTime,endDateTime,page,size);
		return new ResponseEntity<ResponseWrapper> (new ResponseWrapper(SUCCESS.name, smsReport), HttpStatus.OK);
	}
	
	@GetMapping("/getAlertSummaryReport")
	public ResponseEntity<ResponseWrapper> getAlertSummaryReport(@RequestParam("page")int page,@RequestParam("size")int size){
		Page<AlertSummaryReport> alertSummery=reportsService.getAlertSummaryReport(page,size);
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name,alertSummery),HttpStatus.OK);
	}
	
	@GetMapping("/getAlertSummaryDetailsByFilter")
	public ResponseEntity<ResponseWrapper> getAlertSummaryDetailsByFilter(@RequestParam("vltdName")String vltdName,@RequestParam("vehicleRegNo")String vehicleRegNo,@RequestParam("alertName")String alertName,@RequestParam("alertDate")String alertDate,@RequestParam("page")int page,@RequestParam("size")int size){
		Page<AlertSummaryReport> alertSummery=reportsService.getAlertSummaryDetailsByFilter(vltdName,vehicleRegNo,alertName,alertDate,page,size);
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name,alertSummery),HttpStatus.OK);
	}
	
	@GetMapping("/getEmergencyAlertsDetailsByFilter")
	public ResponseEntity<ResponseWrapper> getEmergencyAlertsDetailsByFilter(@RequestParam("vltdName")String vltdName,@RequestParam("vehicleRegNo")String vehicleRegNo,@RequestParam("startDateTime")String startDateTime,@RequestParam("endDateTime")String endDateTime,@RequestParam("page")int page,@RequestParam("size")int size){
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, reportsService.getEmergencyAlertsDetailsByFilter(vltdName, vehicleRegNo,startDateTime,endDateTime,page,size)),
				HttpStatus.OK);
	}
	
	@GetMapping("/getSMSRequestReport")
	public ResponseEntity<ResponseWrapper> getSMSRequestReport(@RequestParam("page")int page,@RequestParam("size")int size){
		Page<SMS_Request_Report_Response> smsRequestreport=reportsService.getSMSRequestReport(page,size);
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name,smsRequestreport),HttpStatus.OK);
	}
	
	@GetMapping("/getSMSRequestDetailsByFilter")
	public ResponseEntity<ResponseWrapper> getSMSRequestDetailsByFilter(@RequestParam("vltdName")String vltdName,@RequestParam("vehicleRegNo")String vehicleRegNo,@RequestParam("startDateTime")String startDateTime,@RequestParam("endDateTime")String endDateTime,@RequestParam("page")int page,@RequestParam("size")int size){
		Page<SMS_Request_Report_Response> smsDetails=reportsService.getSMSRequestDetailsByFilter(vltdName, vehicleRegNo,startDateTime,endDateTime,page,size);
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name,smsDetails),HttpStatus.OK);
	}
	
	@GetMapping("alert/Counts/filter")
	public ResponseEntity<ResponseWrapper> getAlertCountsByFilter(@RequestParam("vltdName")String vltdName,@RequestParam("vehicleRegNo")String vehicleRegNo,@RequestParam(required=false,value= "alertName")String alertName,@RequestParam("Date")Date Date) throws CustomException {
		List<AlertTypeCounts> alertCounts = reportsService.getAlertCountsByFilter(vltdName,vehicleRegNo,alertName,Date);
		Map<String, List<AlertTypeCounts>> alertList = new HashMap<>();
		alertList.put("content", alertCounts);
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name,alertList ),
				HttpStatus.OK);
	}
	
	@GetMapping("/type/Counts")
	public ResponseEntity<ResponseWrapper> getAlertCounts() throws CustomException {
		List<AlertTypeCounts> alertCounts = reportsService.getAlertCounts();
		Map<String, List<AlertTypeCounts>> alertList = new HashMap<>();
		alertList.put("content", alertCounts);
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name,alertList ),
				HttpStatus.OK);
	}
	
	@GetMapping("/getGeofenceInOutReport")
	public ResponseEntity<ResponseWrapper> getGeofenceInOutReport(@RequestParam("page") int page, @RequestParam("size") int size){
		Page<GeofenceInOutReport> geofenceReport=reportsService.getGeofenceInOutReport(page,size);
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name,geofenceReport),HttpStatus.OK);
	}
	
	@GetMapping("/getGeofenceInOutReportByFilter")
	public ResponseEntity<ResponseWrapper> getGeofenceInOutReportByFilter(@RequestParam("vltdName") String vltdName, @RequestParam("vehicleRegNo") String vehicleRegNo, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("page") int page, @RequestParam("size") int size){
		Page<GeofenceInOutReport> geofenceReportByFilter=reportsService.getGeofenceInOutReportByFilter(vltdName,vehicleRegNo,startDate,endDate,page,size);
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name,geofenceReportByFilter),HttpStatus.OK);
	}
	
	@GetMapping("/getAccountBalanceReportByFilter")
	public ResponseEntity<ResponseWrapper> getAccountBalanceReportByFilter(@RequestParam("page")int page,@RequestParam("size") int size,@RequestParam("vltdName") String vltdName,@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate){
		Page<GetAccountBalanceReportResponse> accountBalanceByfilter=reportsService.getAccountBalanceReportByFilter(page,size,vltdName,startDate,endDate);
	    return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name,accountBalanceByfilter),HttpStatus.OK);
	}
	
	@GetMapping("/getAccountSummeryReportByFilter")
	public ResponseEntity<ResponseWrapper> getAccountSummeryReportByFilter(@RequestParam("page")int page,@RequestParam("size") int size,@RequestParam("vltdName") String vltdName,@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate,@RequestParam("searchBy") String searchBy){
		Page<AccountSummeryReportByDevice> accountSummery=reportsService.getAccountSummeryReportByFilter(page,size,vltdName,startDate,endDate,searchBy);
	    return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name,accountSummery),HttpStatus.OK);

	}
}
