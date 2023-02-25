package com.trinetra.reports;

import static com.trinetra.model.enums.Status.SUCCESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trinetra.exception.CustomException;
import com.trinetra.model.entity.EmergencyAlertsReport;
import com.trinetra.model.wrapper.ResponseWrapper;
import com.trinetra.service.ReportsService;

@RestController
@RequestMapping("/api/v1/alert")
public class AlertController {
	
	@Autowired
	private ReportsService reportsService;
	
	@GetMapping("/report/emergencyAlert")
	public ResponseEntity<ResponseWrapper> getEmergencyAlertReport(@RequestParam("page") int page, @RequestParam("size") int size) throws CustomException {
		Page<EmergencyAlertsReport> emergencyReport=reportsService.getEmergencyAlertReport(page, size);
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, emergencyReport),HttpStatus.OK);
	}
}
