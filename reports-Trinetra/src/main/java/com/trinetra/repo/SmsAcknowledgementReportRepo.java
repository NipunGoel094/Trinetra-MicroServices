package com.trinetra.repo;

import com.trinetra.model.entity.SmsAcknowledgementReport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsAcknowledgementReportRepo extends JpaRepository<SmsAcknowledgementReport, Integer>{

	@Query(value = "select * from view_sms_acknowledgement_report where vltd_manufacturer_name = ?1 and recieved_date_time between cast(?2 as timestamp) and cast(?3 as timestamp)", nativeQuery = true)
	Page<SmsAcknowledgementReport> getSmsAcknowledgementDetailsByFilter(String vltdName, String startDateTime,
			String endDateTime, Pageable pageable);
}
