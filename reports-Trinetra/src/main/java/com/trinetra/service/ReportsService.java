package com.trinetra.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.trinetra.exception.CustomException;
import com.trinetra.model.entity.AlertSummaryReport;
import com.trinetra.model.entity.EmergencyAlertsReport;
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
import com.trinetra.repo.AlertSummaryReportRepo;
import com.trinetra.repo.EmergencyAlertsReportRepo;
import com.trinetra.repo.EmergencyResolvedAlertsRepo;
import com.trinetra.repo.GeofenceInOutReportRepo;
import com.trinetra.repo.HistoryLogReportRepo;
import com.trinetra.repo.OverSpeedAlertRepo;
import com.trinetra.repo.ReportsRepo;
import com.trinetra.repo.RouteDeviationReportRepo;
import com.trinetra.repo.SmsAcknowledgementReportRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReportsService {

	@Autowired
	private ReportsRepo reportsRepo;
	
	@Autowired
	private EmergencyResolvedAlertsRepo emergencyResolvedAlertsRepo;
	
	@Autowired
	private EmergencyAlertsReportRepo emergencyAlertsReportRepo;
	
	@Autowired
	private HistoryLogReportRepo historyLogReportRepo;
	
	@Autowired
	private OverSpeedAlertRepo overSpeedAlertRepo;
	
	@Autowired
	private RouteDeviationReportRepo routeDeviationReportRepo;
	
	@Autowired
	private SmsAcknowledgementReportRepo smsAcknowledgementReportRepo;
	
	@Autowired
	private AlertSummaryReportRepo alertSummaryReportRepo;
	
	@Autowired
	private GeofenceInOutReportRepo geofenceInOutReportRepo;
	
	public Page<EmergencyResolvedAlerts> getEmergencyResolvedAlertReport(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<EmergencyResolvedAlerts> emergencyResolvedAlertReport = emergencyResolvedAlertsRepo.findAll(pageable);
		return emergencyResolvedAlertReport;
	}
	
	public Page<EmergencyResolvedAlerts> getEmergencyResolvedAlertReportByFilter(String vltdName, String vehicleRegNo, String startDate,
			String endDate, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		try {
			Page<EmergencyResolvedAlerts> emergencyResolvedAlertReport = emergencyResolvedAlertsRepo.getEmergencyResolvedAlertReportByFilter(vltdName,vehicleRegNo,startDate,endDate,pageable);
			return emergencyResolvedAlertReport;
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error("Exception occurred - Reason: {}", e.getLocalizedMessage());
			List<EmergencyResolvedAlerts> responses = new ArrayList<EmergencyResolvedAlerts>();
			Page<EmergencyResolvedAlerts> emergencyResolvedAlertReport = new PageImpl<EmergencyResolvedAlerts>(responses,pageable,0);
			return emergencyResolvedAlertReport;
		}
	}
	
	public Page<HistoryLogReport> getHistoryLogReport(String vltdName, String vehicleRegNo, String startDateTime, String endDateTime, int page, int size) {
		Pageable pageable=PageRequest.of(page, size);
		Page<HistoryLogReport> historylogReport=historyLogReportRepo.getHistoryLogReport(vltdName,vehicleRegNo,startDateTime,endDateTime,pageable);
		return historylogReport;
	}
	
	public Page<HistoryLogReport> getAllHistoryLogReport(int page, int size) {
		Pageable pageable=PageRequest.of(page, size);
		Page<HistoryLogReport> allHistorylogReport=historyLogReportRepo.findAll(pageable);
		return allHistorylogReport;
	}
	
	public Page<HistoryLogReport> searchByHistoryLogReport(String input, int page, int size) {
		Pageable pageable=PageRequest.of(page, size);
		Page<HistoryLogReport> SearchBy=historyLogReportRepo.searchByHistoryLogReport(input,pageable);
		return SearchBy;
	
	}
	
	public Page<OverSpeedAlert> getOverSpeedAlertReport(int page, int size) {
		Pageable pageable=PageRequest.of(page, size);
		Page<OverSpeedAlert> overSpeedAlertReport=overSpeedAlertRepo.findAll(pageable);
		return overSpeedAlertReport;
	}
	
	public Page<OverSpeedAlert> getOverSpeedAlertDetailsByFilter(String vltdName, String vehicleRegNo,
			String startDateTime, String endDateTime, int page, int size) {
		Pageable pageable=PageRequest.of(page, size);
		Page<OverSpeedAlert> overSpeedAlertReport=overSpeedAlertRepo.getOverSpeedAlertDetailsByFilter(vltdName,vehicleRegNo,startDateTime,endDateTime,pageable);
		log.debug("{} for the over speed alert report ",overSpeedAlertReport);
		return overSpeedAlertReport;
	}
	
	public Page<RouteDeviationReport> getRouteDeviationReport(int page, int size) {
		Pageable pageable=PageRequest.of(page, size);
		Page<RouteDeviationReport> routeDeviationReport=routeDeviationReportRepo.findAll(pageable);
		return routeDeviationReport;
	}
	
	public Page<RouteDeviationReport> getRouteDeviationDetailsByFilter(String routeName,
			String vehicleRegNo, String startDateTime, String endDateTime, int page, int size) {
		Pageable pageable=PageRequest.of(page, size);
		Page<RouteDeviationReport>routedeviation=routeDeviationReportRepo.getRouteDeviationDetailsByFilter(routeName,
				vehicleRegNo,startDateTime,endDateTime,pageable);
		return routedeviation;
	}
	
	public String saveEmergencyResolved(EmergencyResolveRequest emergencyResolveRequest) {
		try{
			reportsRepo.insertInEmergencyResolve(emergencyResolveRequest);
		}
		catch(Exception e) {
			e.printStackTrace();
			return "Error while inserting in Emergency Resolve";
		}
		return "Successfully Inserted in Emergency Resolve";
	}
	
	public Page<SmsAcknowledgementReport> getSmsAcknowledgementReport(int page, int size) {
		Pageable pageable=PageRequest.of(page, size);
		Page<SmsAcknowledgementReport> smsReport=smsAcknowledgementReportRepo.findAll(pageable);
		log.info("{} for Sms acknowledgement report Details", smsReport);
		return smsReport;
	}
	
	public Page<SmsAcknowledgementReport> getSmsAcknowledgementDetailsByFilter(String vltdName,
			String startDateTime, String endDateTime, int page, int size) {
		Pageable pageable=PageRequest.of(page, size);
		Page<SmsAcknowledgementReport> smsReport=smsAcknowledgementReportRepo.getSmsAcknowledgementDetailsByFilter(vltdName,startDateTime,endDateTime,pageable);
		log.debug("{} for Sms acknowledgement report Details by vltdName and start & end date {} ", smsReport);
		return smsReport;
	}
	
	public Page<AlertSummaryReport> getAlertSummaryReport(int page, int size) {
		Pageable pageable=PageRequest.of(page, size);
		Page<AlertSummaryReport> alertReport=alertSummaryReportRepo.findAll(pageable);
		log.info("{} for alert summary report",alertReport);
		return alertReport;
	}
	
	public Page<AlertSummaryReport> getAlertSummaryDetailsByFilter(String vltdName, String vehicleRegNo,
			String alertName, String alertDate, int page, int size) {
		Pageable pageable=PageRequest.of(page, size);
		Page<AlertSummaryReport> alertSummary=alertSummaryReportRepo.getAlertSummaryDetailsByFilter(vltdName,vehicleRegNo,alertName,alertDate,pageable);
		log.debug("{}for alert summary report by vltd,vehicleregno,alertname and alertdate",alertSummary);
		return alertSummary;
	}
	
	public Page<EmergencyAlertsReport> getEmergencyAlertReport(int page, int size) throws CustomException {
		try {
			Pageable pageable=PageRequest.of(page, size);
			Page<EmergencyAlertsReport> emergencyReport=emergencyAlertsReportRepo.findAll(pageable);
			return emergencyReport;
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception occurred - Reason: {}", e.getLocalizedMessage());
			throw new CustomException(e.getLocalizedMessage());
		}
	}
	
	public Page<EmergencyAlertsReport> getEmergencyAlertsDetailsByFilter(String vltdName, String vehicleRegNo, String startDateTime,
			String endDateTime, int page, int size) throws CustomException {
		try {
			Pageable pageable=PageRequest.of(page, size);
			Page<EmergencyAlertsReport> getEmergencyAlerts=emergencyAlertsReportRepo.getEmergencyAlertsDetailsByFilter(vltdName, vehicleRegNo,startDateTime,endDateTime,pageable);
			return getEmergencyAlerts;
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception occurred - Reason: {}", e.getLocalizedMessage());
			throw new CustomException(e.getLocalizedMessage());
		}
	}
	
	public Page<SMS_Request_Report_Response> getSMSRequestReport(int page, int size) {
		Pageable pageable=PageRequest.of(page, size);
		Page<SMS_Request_Report_Response> smsRequestReport=reportsRepo.getSMSRequestReport(pageable);
		return smsRequestReport;
	}
	
	public Page<SMS_Request_Report_Response> getSMSRequestDetailsByFilter(String vltdName, String vehicleRegNo,
			String startDateTime, String endDateTime, int page, int size) {
		Pageable pageable=PageRequest.of(page, size);
		Page<SMS_Request_Report_Response> smsReport=reportsRepo.getSMSRequestDetailsByFilter(vltdName,vehicleRegNo,startDateTime,endDateTime,pageable);
		return smsReport;
	}
	
	public List<AlertTypeCounts> getAlertCounts() throws CustomException {
		List<AlertTypeCounts> alertCount = reportsRepo.getAlertTypeCounts();
		return alertCount;	
	}
	
	public List<AlertTypeCounts> getAlertCountsByFilter(String vltdName, String vehicleRegNo, String alertName, Date date) {
		List<AlertTypeCounts> alertCount=null;
		if(alertName==null || alertName.equalsIgnoreCase("all") ){
	     alertCount = reportsRepo.getAlertTypeByFilter(vltdName,vehicleRegNo,date); 
	    }else{
		 alertCount = reportsRepo.getAlertTypeCountsByFilter(vltdName,vehicleRegNo,alertName,date);
	    }
		return alertCount;
	}
	
	public Page<GeofenceInOutReport> getGeofenceInOutReport(int page, int size) {
		Pageable pageable=PageRequest.of(page, size);
		Page<GeofenceInOutReport> geofenceInOutReport=geofenceInOutReportRepo.findAll(pageable);
		return geofenceInOutReport;
	}
	
	public Page<GeofenceInOutReport> getGeofenceInOutReportByFilter(String vltdName, String vehicleRegNo, String startDate,
			String endDate, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		try {
			Page<GeofenceInOutReport> geofenceInOutReportByFilter = geofenceInOutReportRepo.getGeofenceInOutReportByFilter(vltdName,vehicleRegNo,startDate,endDate,pageable);
			return geofenceInOutReportByFilter;
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error("Exception occurred - Reason: {}", e.getLocalizedMessage());
			List<GeofenceInOutReport> responses = new ArrayList<GeofenceInOutReport>();
			Page<GeofenceInOutReport> geofenceInOutReportByFilter = new PageImpl<GeofenceInOutReport>(responses,pageable,0);
			return geofenceInOutReportByFilter;
		}
	}
	
	public Page<GetAccountBalanceReportResponse> getAccountBalanceReportByFilter(int page, int size, String vltdName,
			String startDate, String endDate) {
		Pageable pageable=PageRequest.of(page, size);
		Page<GetAccountBalanceReportResponse> accountBalance=reportsRepo.getAccountBalanceReportByFilter(pageable,vltdName,startDate,endDate);
		return accountBalance;
	}
	
	public Page<AccountSummeryReportByDevice> getAccountSummeryReportByFilter(int page, int size,String vltdName, String startDate,
			String endDate, String searchBy) {
		Pageable pageable=PageRequest.of(page,size);
		Page<AccountSummeryReportByDevice> paginatedResponses=null;
		List<AccountSummeryReportByDevice> finalResult=new ArrayList<AccountSummeryReportByDevice>();
	    AccountSummeryReportByDevice response=new AccountSummeryReportByDevice();

		
		if(searchBy.equalsIgnoreCase("Device")) {
			Page<Object[]> deviceTransaction=reportsRepo.getDeviceTransactionDetails(pageable,vltdName,startDate,endDate);
			long totalRowCount = deviceTransaction.getTotalElements();
			List<Object[]> list = deviceTransaction.getContent();
			for (int i = 0; i < list.size(); i++) {
				Object[] cols = list.get(i);
				response.setVltdName((String) cols[0]);
			    response.setDealerName((String) cols[1]);
			    response.setPersonName((String) cols[2]);
			    response.setVehicleRegNo((String) cols[3]);
			    response.setDeviceImei((String) cols[4]);
			    response.setDeviceIccid((String) cols[5]);
			    response.setVehicleType((String) cols[6]);
			    response.setAmount((String) cols[7]);
			    response.setTranactionDate((String) cols[8]);
			    finalResult.add(response);
			 	paginatedResponses = new PageImpl<>(finalResult, pageable, totalRowCount);

			}
			
		}
		else {
			Page<Object[]> vltdRecharge=reportsRepo.getVltdRechargeDetails(pageable,vltdName,startDate,endDate);
			long totalRowCount = vltdRecharge.getTotalElements();
			List<Object[]> list = vltdRecharge.getContent();
			for (int i = 0; i < list.size(); i++) {
				Object[] cols = list.get(i);
			response.setVltdName((String) cols[0]);
			 response.setAmount((String) cols[1]);
			 response.setRechargeDate((String) cols[2]);
			 finalResult.add(response);
			 	paginatedResponses = new PageImpl<>(finalResult, pageable, totalRowCount);
			 }
		}
		return paginatedResponses;
	}
}
