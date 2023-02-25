package com.trinetra.repo;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.SansoOtp;

@Repository
@Transactional
public interface SansoOtpRepo extends JpaRepository<SansoOtp, Integer>{

	@Query(value="select created_date from sanso_otp where mobile_no=:mobileNumber and otp=:otp",nativeQuery = true)
	Timestamp getDateTime(@Param("mobileNumber") String mobileNumber,@Param("otp") String otp);

	@Modifying
	@Query(value="update sanso_otp  set updated_date =?3, validate_check=true where mobile_no =?1 and otp =?2",nativeQuery = true)
	void updateVarifiedOtp(@Param("mobileNumber") String mobileNumber,@Param("otp") String otp,@Param("updateDate") Timestamp updateDate);

	@Query(value="select updated_date from sanso_otp where mobile_no=:mobileNumber and otp=:otp",nativeQuery = true)
	Timestamp getupdateDateDateTime(String mobileNumber, String otp);
}
