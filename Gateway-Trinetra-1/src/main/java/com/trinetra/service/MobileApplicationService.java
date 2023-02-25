package com.trinetra.service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trinetra.exception.CustomException;
import com.trinetra.model.entity.SansoOtp;
import com.trinetra.model.entity.SimSMS;
import com.trinetra.model.entity.User;
import com.trinetra.model.page.response.MISDealerDetailsForMobile;
import com.trinetra.model.page.response.MisMobileDealerBasicData;
import com.trinetra.model.page.response.MobileDeviceDetailsResponse;
import com.trinetra.model.page.response.MobileGroupViewVehicleResponse;
import com.trinetra.model.page.response.MobileHistoryLogReportResponse;
import com.trinetra.model.page.response.MobileLiveEmergencyResponse;
import com.trinetra.model.page.response.MobileRouteDeviationVehicleData;
import com.trinetra.model.page.response.SimSmsResponse;
import com.trinetra.model.page.response.VehicleDetailsForMobile;
import com.trinetra.model.request.ForgotPasswordRequest;
import com.trinetra.model.request.MobileValidateOtpRequest;
import com.trinetra.model.request.ResetPasswordRequest;
import com.trinetra.model.response.MailResponse;
import com.trinetra.model.response.Message;
import com.trinetra.repo.MobileApplicationRepo;
import com.trinetra.repo.SansoOtpRepo;
import com.trinetra.repo.UserRepository;
import com.trinetra.utils.ThirdpartyApiUtility;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MobileApplicationService {

	private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;
	
	@Autowired
	private MobileApplicationRepo mobileApplicationRepo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ThirdpartyApiUtility thirdpartyApiUtility;
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private SansoOtpRepo sansoOtpRepo;
	
	@Value("${sensorise.api.base.url}")
	private String sensoriseBaseUrl;
	
	@Value("${taisys.service.base.url}")
	private String taisysBaseUrl;
	
	@Value("${sensorise.api.cryptoLogic}")
	private String sensoriseCryptoLogic;
	
	@Value("${sensorise.api.preshared.key}")
	private String sensorisePresharedKey;
	
	@Value("${sensorise.api.corelation.id}")
	private String sensoriseCorelationId;
	
	@Value("${sensorise.api.token.validity}")
	private int sensoriseTokenValidity;
	
	@Value("${sensorise.api.token.transactions}")
	private int sensoriseTokenTransactions;
	
	@Value("${sensorise.api.realtion.id}")
	private String sensoriseApiRealtionId;
	
	@Value("${sensorise.api.shared.key}")
	private String sensoriseApiSharedKey;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public String getDeviceVehicleDataByUserId(long userId) {
		if(!userRepo.findById(userId).isPresent()) {
			return "User Id not Found";
		}
		String response = mobileApplicationRepo.getDeviceVehicleDataByUserId(userId);
		if(response == null || response.isEmpty()) {
			return "No Data Found.";
		}
		return response;
	}

	public Object forgotPassword(int key, ForgotPasswordRequest forgotPasswordRequest) {
		
		if(key == 0) {
			String mobileNo = forgotPasswordRequest.getMobileNo();
			String operator = "BSNL";
			Pattern mobileNoPattern = Pattern.compile("[0-9]+");			
			if(mobileNo!=null && !mobileNo.isBlank() && mobileNoPattern.matcher(mobileNo).matches()) {
				List<User> userListByMobileNo = userRepo.findBymobileNumber(mobileNo);
				if(userListByMobileNo.isEmpty()) {
					return new Message("Invalid Mobile No.");
				}
				SimSMS sms = new SimSMS(mobileNo, "Your OTP for Setting new Password for Trinetra is ", operator);
				SimSmsResponse otpResponse = callSensoriseSMSApi(sms);
				return otpResponse;
			}
			else {
				return new Message("Invalid Mobile No.");
			}
		}
		
		else if(key == 1){
			String email = forgotPasswordRequest.getEmail();
			if(email!=null && !email.isBlank() && email.contains("@")) {
				MailResponse mailResponse = userService.forgotPassword(email, 1);
				return mailResponse;
			}
			else {
				return new Message("Invalid Email.");
			}
		}
		return new Message("Invalid Key.");
	}
	
	public SimSmsResponse callSensoriseSMSApi(SimSMS sms) {
		SimSmsResponse simSms=new SimSmsResponse();
		SimSMS smspayload=new SimSMS();
		int otplenth=4;
		char[] otp = OTP(otplenth);
		String str =sms.getPayload()+" "+ String.valueOf(otp)+". Regards TNT Team.";
		String otpSavedDB=String.valueOf(otp);
		
		String msisdn=sms.getMsisdn();
		simSms.setMsisdn(msisdn);
        sms.setPayload(str);
		
		smspayload.setMsisdn(msisdn);
		smspayload.setOperator(sms.getOperator());
		smspayload.setPayload(str);
		
		SansoOtp sanso=new SansoOtp();
		sanso.setMobileNo(msisdn);
		sanso.setOtp(otpSavedDB);
		sanso.setSms(str);
		Timestamp createdDate=Timestamp.from(Instant.now());
		sanso.setCreatedDate(createdDate);
		sansoOtpRepo.save(sanso);
		
		simSms = callSensoriseSMS(smspayload);
		
		
		return simSms;
	}
	
	public SimSmsResponse callSensoriseSMS(SimSMS simValidityResponse) {
		try {
			SimSmsResponse simSmsResponse = null;
			
			simSmsResponse = thirdpartyApiUtility.checkOrsacSMS(sensoriseBaseUrl,simValidityResponse,sensoriseCryptoLogic, sensoriseApiSharedKey, sensoriseApiRealtionId, sensoriseTokenValidity, sensoriseTokenTransactions);
				log.info("SensoriseApiResponse: {}", simSmsResponse);

				if (simSmsResponse != null) {
					simSmsResponse.getMsisdn();
				}
			
			return simSmsResponse;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("Exception occurred while processing SMS.");
		}
	}
	
	public char[] OTP(int len)
    {
        
  
		 char[] otp = new char[len];
        String numbers = "0123456789";
  
        Random rndm_method = new Random();
  
      
  
        for (int i = 0; i < len; i++)
        {
           
        	 otp[i] =
                     numbers.charAt(rndm_method.nextInt(numbers.length()));
        }
        return otp;
    }
	
	public Message validateOtp(MobileValidateOtpRequest mobileValidateOtpRequest) {
		String mobileNumber = mobileValidateOtpRequest.getMobileNumber();
		String otp = mobileValidateOtpRequest.getOtp();
		String password = mobileValidateOtpRequest.getPassword();
		if(mobileNumber == null || mobileNumber.isBlank() || !mobileNumber.matches("[0-9]+")) {
			return new Message("Invalid Mobile No.");
		}
		List<User> userListByMobileNo = userRepo.findBymobileNumber(mobileNumber);
		if(userListByMobileNo.isEmpty()) {
			return new Message("Invalid Mobile No.");
		}
		Timestamp createdTime=Timestamp.from(Instant.now());
		String message;
		Timestamp diffTime=sansoOtpRepo.getDateTime(mobileNumber,otp);
		
		long milliDifferent=createdTime.getTime()- diffTime.getTime();
		
		long diff=milliDifferent / 6000 / 10;
		Timestamp updateDate=sansoOtpRepo.getupdateDateDateTime(mobileNumber,otp);
		
		if(updateDate==null) {
			if(diff>15) {
				log.info("Time Expired - 15 min are over"+diff);
				updateVerifiedOtp(mobileNumber,otp);
				return new Message("Time Expired - 15 min are over");
				
			}else {
				log.info("OTP is Validated"+diff);
				message="OTP is Validated.";
				updateVerifiedOtp(mobileNumber,otp);
				User user = userListByMobileNo.get(0);
				user.setPassword(encoder.encode(password));
				userRepo.save(user);
				message += "Password updation successful";
				return new Message(message);
			}
		}
		else {
			return new Message("OTP is Already Validated");
		}
	}
	
	public void updateVerifiedOtp(String mobileNumber,String otp) {
		Timestamp updateDate=Timestamp.from(Instant.now());
		sansoOtpRepo.updateVarifiedOtp(mobileNumber,otp,updateDate);
	}
	
	public Object getLiveEmergencyDetailsByUser(long userId, int page, int size) {
		List<MobileLiveEmergencyResponse> responses = new ArrayList<MobileLiveEmergencyResponse>();
		Optional<User> userOptional = userRepo.findById(userId);
		if(userOptional.isPresent()) {
			responses =  mobileApplicationRepo.getLiveEmergencyListForPermitHolder(userId);
			if(responses.isEmpty()) {
				return new Message("Live Emergency Data Not Found.");
			}
			PagedListHolder<MobileLiveEmergencyResponse> paginatedResponses = new PagedListHolder<MobileLiveEmergencyResponse>(responses);
			paginatedResponses.setPage(page);
			paginatedResponses.setPageSize(size);
			Map<String, Object> paginatedMap = new HashMap<String, Object>();
			paginatedMap.put("content", paginatedResponses.getPageList());
			paginatedMap.put("numberOfElements", paginatedResponses.getPageList().size());
			paginatedMap.put("totalNumberOfElements", paginatedResponses.getNrOfElements());
			paginatedMap.put("totalPages", paginatedResponses.getPageCount());
			paginatedMap.put("page", page);
			paginatedMap.put("size", size);
			return paginatedMap;
		}
		else {
			return new Message("User Id Not Found.");
		}
	}

	public Object getLiveEmergencyDetailsByVehicleRegNoForPermitHolder(String vehicleRegNo) {
		if(vehicleRegNo != null && !vehicleRegNo.isBlank()) {
			List<MobileLiveEmergencyResponse> response = mobileApplicationRepo.getLiveEmergencyListByVehicleNo(vehicleRegNo);
			if(response.isEmpty()) {
				return new Message("Live Emergency Vehicle Data For '"+vehicleRegNo+"' Not Found.");
			}
			else return response;
		}
		log.error("error in MobileApplicationService : {}", "Empty String Search Not Allowed!!");
		return new Message("Empty String Search Not Allowed!!");
	}
	
	

	public Object getVehicleByVehicleRegNo(String vehicleRegNo) {
		List<VehicleDetailsForMobile> vehicleMasters = null;
		
		if (null != vehicleRegNo && !vehicleRegNo.isBlank()) {
			vehicleMasters = mobileApplicationRepo.vehicleDetailsByvehicleNo(vehicleRegNo);
			if(vehicleMasters.isEmpty()) {
				return new Message("VehicleMaster '"+vehicleRegNo+"'not Found!!");
			}
		}
		else {
			return new Message("Empty String Search Not Allowed!!");
		}
		
		return vehicleMasters;
	}

	public Object getVehicleDetailsformobile(int page, int size) {
		
		List<VehicleDetailsForMobile> list =  mobileApplicationRepo.vehicleDetailsByvehicleNo();
		if(list.isEmpty()) {
			return new Message("Vehicle data not Found!!");
		}
		log.debug("size of vehicleMasters {} getvehicleMastersDetails", list.size());
		PagedListHolder<VehicleDetailsForMobile> paginatedResponses = new PagedListHolder<>(list);
		paginatedResponses.setPage(page);
		paginatedResponses.setPageSize(size);
		Map<String, Object> paginatedMap = new HashMap<String, Object>();
		paginatedMap.put("content", paginatedResponses.getPageList());
		paginatedMap.put("numberOfElements", paginatedResponses.getPageList().size());
		paginatedMap.put("totalNumberOfElements", paginatedResponses.getNrOfElements());
		paginatedMap.put("totalPages", paginatedResponses.getPageCount());
		paginatedMap.put("page", page);
		paginatedMap.put("size", size);
		return paginatedMap;
		
	}
	
	public Object getVehicleDetailsForPermitHolder(int page, int size, long userId) {
		User user = userRepo.findById(userId).orElse(null);
		if(user == null) {
			return new Message("User Id Not Found.");
		}
		if(user.getUserTypeId() != 326) {
			return new Message("User Id Not Permit Holder.");
		}
		List<VehicleDetailsForMobile> list =  mobileApplicationRepo.vehicleDetailsByvehicleNo(userId);
		if(list.isEmpty()) {
			return new Message("Vehicle data not Found!!");
		}
		log.debug("size of vehicleMasters {} getvehicleMastersDetails", list.size());
		PagedListHolder<VehicleDetailsForMobile> paginatedResponses = new PagedListHolder<>(list);
		paginatedResponses.setPage(page);
		paginatedResponses.setPageSize(size);
		Map<String, Object> paginatedMap = new HashMap<String, Object>();
		paginatedMap.put("content", paginatedResponses.getPageList());
		paginatedMap.put("numberOfElements", paginatedResponses.getPageList().size());
		paginatedMap.put("totalNumberOfElements", paginatedResponses.getNrOfElements());
		paginatedMap.put("totalPages", paginatedResponses.getPageCount());
		paginatedMap.put("page", page);
		paginatedMap.put("size", size);
		return paginatedMap;
		
	}
	
	public Object getLiveEmergencyDetails(int page, int size) {
		List<MobileLiveEmergencyResponse> response = mobileApplicationRepo.getLiveEmergencyListForPoliceUser();
		if (response.isEmpty()) {
			return new Message("Live Emergency Data Not Found.");
		}
		PagedListHolder<MobileLiveEmergencyResponse> paginatedResponses = new PagedListHolder<MobileLiveEmergencyResponse>(
				response);
		paginatedResponses.setPage(page);
		paginatedResponses.setPageSize(size);
		Map<String, Object> paginatedMap = new HashMap<String, Object>();
		paginatedMap.put("content", paginatedResponses.getPageList());
		paginatedMap.put("numberOfElements", paginatedResponses.getPageList().size());
		paginatedMap.put("totalNumberOfElements", paginatedResponses.getNrOfElements());
		paginatedMap.put("totalPages", paginatedResponses.getPageCount());
		paginatedMap.put("page", page);
		paginatedMap.put("size", size);
		return paginatedMap;
	}
	
	public Object getLiveEmergencyDetailsByVehicleRegNo(String vehicleRegNo) {
		if(vehicleRegNo != null && !vehicleRegNo.isBlank()) {
			List<MobileLiveEmergencyResponse> response = mobileApplicationRepo.getLiveEmergencyListByVehicleNo(vehicleRegNo);
			if(response.isEmpty()) {
				return new Message("Live Emergency Vehicle Data For '"+vehicleRegNo+"' Not Found.");
			}
			return response;
		}
		log.error("error in MobileApplicationService : {}", "Empty String Search Not Allowed.");
		return new Message("Empty String Search Not Allowed.");
	}
	
	public Object getAllDeviceData(int page, int size, long userId) throws CustomException {
		Pageable pageable = PageRequest.of(page, size);
		Optional<User> userOptional = userRepo.findById(userId);
		if(userOptional.isPresent()) {
			Page<MobileDeviceDetailsResponse> list = mobileApplicationRepo.getDeviceList(pageable,userId);
			int totalRowCount = mobileApplicationRepo.getDeviceListCount(userId);
			if (list.isEmpty()) {
				return new Message("No Device data found!");
			}
			Map<String, Object> paginatedMap = new HashMap<String, Object>();
			paginatedMap.put("content", list.getContent());
			paginatedMap.put("numberOfElements", list.getContent().size());
			paginatedMap.put("totalNumberOfElements", totalRowCount);
			paginatedMap.put("totalPages", list.getTotalPages());
			paginatedMap.put("page", page);
			paginatedMap.put("size", size);
			return paginatedMap;
		}
		else {
			return new Message("User Id Not Found.");
		}
	}
	
	public Object getConnectedDeviceDetailsList(int page, int size, long userId) {
		Optional<User> userOptional = userRepo.findById(userId);
		if(userOptional.isPresent()) {
			List<MobileDeviceDetailsResponse> list =  mobileApplicationRepo.getConnectedDevicesList(userId);
			if(list.isEmpty()) {
				return new Message("No Device Data Found.");
			}
			PagedListHolder<MobileDeviceDetailsResponse> paginatedResponses = new PagedListHolder<MobileDeviceDetailsResponse>(list);
			paginatedResponses.setPage(page);
			paginatedResponses.setPageSize(size);
			Map<String, Object> paginatedMap = new HashMap<String, Object>();
			paginatedMap.put("content", paginatedResponses.getPageList());
			paginatedMap.put("numberOfElements", paginatedResponses.getPageList().size());
			paginatedMap.put("totalNumberOfElements", paginatedResponses.getNrOfElements());
			paginatedMap.put("totalPages", paginatedResponses.getPageCount());
			paginatedMap.put("page", page);
			paginatedMap.put("size", size);
			return paginatedMap;
		}
		else {
			return new Message("User Id Not Found.");
		}
	}
	
	public Object getDownvehicleDetails(int page,int size, long userId) {
		Optional<User> userOptional = userRepo.findById(userId);
		if(userOptional.isPresent()) {
			List<MobileDeviceDetailsResponse> downVehicleResponceList=mobileApplicationRepo.getDownVehicleList(userId);
			if(downVehicleResponceList.isEmpty()) {
				return new Message("No Device Data Found.");
			}
			PagedListHolder<MobileDeviceDetailsResponse> paginatedResponses = new PagedListHolder<MobileDeviceDetailsResponse>(downVehicleResponceList);
			paginatedResponses.setPage(page);
			paginatedResponses.setPageSize(size);
			Map<String, Object> paginatedMap = new HashMap<String, Object>();
			paginatedMap.put("content", paginatedResponses.getPageList());
			paginatedMap.put("numberOfElements", paginatedResponses.getPageList().size());
			paginatedMap.put("totalNumberOfElements", paginatedResponses.getNrOfElements());
			paginatedMap.put("totalPages", paginatedResponses.getPageCount());
			paginatedMap.put("page", page);
			paginatedMap.put("size", size);
			return paginatedMap;
		}
		else {
			return new Message("User Id Not Found.");
		}
	}

	public Object filterByDeviceImeiId(String deviceImei) throws CustomException {
		
		List<MobileDeviceDetailsResponse> data = new ArrayList<MobileDeviceDetailsResponse>();
		if(deviceImei != null && !deviceImei.isBlank()) 
			data = mobileApplicationRepo.findByImeiId(deviceImei.toLowerCase());
		else {
			return new Message("Device Imei Cannot Be Empty String.");
		}
		if(data.isEmpty()) {
			return new Message("Device Data For Imei'"+deviceImei+"' Not Found.");
		}
		return data;
	}
	
	public Object getAllHistoryLogReport(long userId) {
		Optional<User> userOptional = userRepo.findById(userId);
		if(userOptional.isPresent()) {
			List<MobileHistoryLogReportResponse> allHistorylogReport=mobileApplicationRepo.getAllHistoryLogReport(userId);
			if(allHistorylogReport.isEmpty()) {
				return new Message("History Data Not Found.");
			}
			return allHistorylogReport;
		}
		else {
			return new Message("User Id Not Found.");
		}
	}
	
	public Object getHistoryLogReport(String vehicleRegNo, String startDateTime, String endDateTime, int page, int size) {
		if(vehicleRegNo != null && !vehicleRegNo.isBlank() && startDateTime != null && !startDateTime.isBlank() && endDateTime != null && !endDateTime.isBlank()) {
			List<MobileHistoryLogReportResponse> historylogReport=mobileApplicationRepo.getHistoryLogReport(vehicleRegNo,startDateTime,endDateTime);
			if(historylogReport.isEmpty()) {
				return new Message("Vehicle History Data For '"+vehicleRegNo+"' Not Found.");
			}
			PagedListHolder<MobileHistoryLogReportResponse> paginatedResponses = new PagedListHolder<MobileHistoryLogReportResponse>(historylogReport);
			paginatedResponses.setPage(page);
			paginatedResponses.setPageSize(size);
			Map<String, Object> paginatedMap = new HashMap<String, Object>();
			paginatedMap.put("content", paginatedResponses.getPageList());
			paginatedMap.put("numberOfElements", paginatedResponses.getPageList().size());
			paginatedMap.put("totalNumberOfElements", paginatedResponses.getNrOfElements());
			paginatedMap.put("totalPages", paginatedResponses.getPageCount());
			paginatedMap.put("page", page);
			paginatedMap.put("size", size);
			return paginatedMap;
		}
		else {
			return new Message("Empty String Search Not Allowed.");
		}
	}

	public Object getDeviatedVehicleDataByVehicleRegNo(String vehicleRegNo) {
		if(vehicleRegNo != null && !vehicleRegNo.isBlank()) {
			List<MobileRouteDeviationVehicleData> routeDeviationData = mobileApplicationRepo.getDeviatedVehicleDataByVehicleRegNo(vehicleRegNo);
			if(routeDeviationData.isEmpty()) {
				return new Message("Route Deviation Data Not Found for Vehicle '"+vehicleRegNo+"'.");
			}
			return routeDeviationData;
		}
		else {
			return new Message("Empty String Not Allowed.");
		}
	}

	public Object getDeviatedVehicleData() {
		List<MobileRouteDeviationVehicleData> deviatedVehicleData = mobileApplicationRepo.getDeviatedVehicleData();
		if(deviatedVehicleData.isEmpty()) {
			return new Message("Route Deviation Data Not Found.");
		}
		return deviatedVehicleData;
	}
	
	public Object getDetailsByStateIdAndRto(int stateId, int rtoId) {
		
		List<MobileGroupViewVehicleResponse> detailsByStateAndRto = mobileApplicationRepo.getDetailsByStateIdAndRto(stateId,rtoId);
		if(detailsByStateAndRto.isEmpty()) {
			return new Message("State and Rto Details Not Found.");
		}
		return detailsByStateAndRto;
	}
	
	public Message resetPassword(ResetPasswordRequest resetPasswordRequest) {
		String token = resetPasswordRequest.getToken();
		String password = resetPasswordRequest.getPassword();
		Optional<User> userOptional = Optional.ofNullable(userRepo.findByResetPasswordToken(token));

		if (!userOptional.isPresent()) {
			return new Message("Invalid token");
		}

		LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();

		if (isTokenExpired(tokenCreationDate)) {
			return new Message("Token expired");
		}
		User user = userOptional.get();

		user.setPassword(encoder.encode(password));
		user.setResetPasswordToken(null);
		user.setTokenCreationDate(null);
		userRepo.save(user);
		return new Message("Your password updated Successfully");
	}
	
	private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

		LocalDateTime now = LocalDateTime.now();
		Duration diff = Duration.between(tokenCreationDate, now);

		return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
	}
	
	
	
	
	public Object getMISDealerDatabyOemUser(int page, int size,int userid) {
		
		List<MisMobileDealerBasicData> list =  mobileApplicationRepo.getMISDealerDatabyOemUser(userid);
		if(list.isEmpty()) {
			return new Message("Dealer data not Found!!");
		}
		log.debug("size of dealer basic data {} getMISDealerDatabyOemUser", list.size());
		PagedListHolder<MisMobileDealerBasicData> paginatedResponses = new PagedListHolder<>(list);
		paginatedResponses.setPage(page);
		paginatedResponses.setPageSize(size);
		Map<String, Object> paginatedMap = new HashMap<String, Object>();
		paginatedMap.put("content", paginatedResponses.getPageList());
		paginatedMap.put("numberOfElements", paginatedResponses.getPageList().size());
		paginatedMap.put("totalNumberOfElements", paginatedResponses.getNrOfElements());
		paginatedMap.put("totalPages", paginatedResponses.getPageCount());
		paginatedMap.put("page", page);
		paginatedMap.put("size", size);
		return paginatedMap;
		
	}

	public Object getMisDealerDetailsByDealerCode(String dealerCode) {
		List<MISDealerDetailsForMobile> data = new ArrayList<MISDealerDetailsForMobile>();
		if(dealerCode != null && !dealerCode.isBlank()) 
			data = mobileApplicationRepo.findBydealerCode(dealerCode);
		else {
			return new Message("Dealer Code Cannot Be Empty String.");
		}
		if(data.isEmpty()) {
			return new Message("Dealer Data For dealerCode'"+dealerCode+"' Not Found.");
		}
		return data;
	}
	
	
	public Object getVehicleDetailsForPermitHolder(long userId) {
		User user = userRepo.findById(userId).orElse(null);
		if(user == null) {
			return new Message("User Id Not Found.");
		}
		if(user.getUserTypeId() != 326) {
			return new Message("User Id Not Permit Holder.");
		}
		List<VehicleDetailsForMobile> list =  mobileApplicationRepo.vehicleDetailsByvehicleNo(userId);
		if(list.isEmpty()) {
			return new Message("Vehicle data not Found!!");
		}
		log.debug("size of vehicleMasters {} getvehicleMastersDetails", list.size());
		return list;
		
	}
}
