package com.poc.serviceImpl;

import static com.poc.constants.UtilConstants.SAVE;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.poc.controller.RegistrationController;
//import com.poc.model.MailDTO;
import com.poc.reg.model.Registration;
import com.poc.repository.RegistrationRepository;
import com.poc.request.RegistrationRequest;
import com.poc.response.ErrorResponse;
import com.poc.response.SuccessResponse;
import com.poc.service.FeignService;
import com.poc.service.RegistrationService;

import feign.Response;


@Service
public class RegistrationServiceImpl implements RegistrationService {
	
	Logger logger = Logger.getLogger(RegistrationController.class);
	@Autowired
	private RegistrationRepository registrationRepository;

	@Autowired
	Environment env;
	
	@Autowired
	private FeignService service;

	@Override
	public ResponseEntity<Object> saveDetails(RegistrationRequest registrationRequest) {
		
		try {
		String val = "" + ((int) (Math.random() * 9000) + 1000);

		Registration registration = registrationRequest.getRegistration();
		registration.setOtp(val);
		if(registrationRequest.getRegistration().getEmailId()!=null && registrationRequest.getRegistration().getPhoneNumber()!=null
				&& registrationRequest.getNotificationType().equalsIgnoreCase(SAVE)) {
		registrationRepository.save(registration);
		
		String emailId = registrationRequest.getRegistration().getEmailId();
		String otp = registrationRequest.getRegistration().getOtp();
		
		
		System.out.println("your mail :"+emailId);
		System.out.println("your OTPPPP :"+otp);
		
		String mailRequest="{\"mail\":{\"toMail\":\""+emailId +" \",\"otp\":\"" + otp +"\"},\"notificationType\":\"mail\"}";
		
		Response obj = service.getEmails(mailRequest);
		

		
		obj.close();
		}


		SuccessResponse successResponse = new SuccessResponse();
		successResponse.setMessage("OTP send successFully");
		successResponse.setStatusCode("200");
		return new ResponseEntity<>(successResponse, HttpStatus.OK);
		}catch (Exception e) {

			logger.error("Exception caught : " + e.getMessage());
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setMessage("Exception caught!");
			error.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
		
	}

	@Override
	public ResponseEntity<Object> verifyOTP(RegistrationRequest registrationRequest) {
		Registration registrationId = registrationRepository
				.getByRegistrationId(registrationRequest.getRegistration().getRegistrationId());
		boolean verify = false;
		if (registrationId != null) {
			verify = verify(registrationRequest.getRegistration().getOtp(), registrationId.getOtp());

		}
		if (!verify) {
			ErrorResponse err = new ErrorResponse();
			err.setMessage("Invalid OTP");
			err.setStatusCode("409");
			return new ResponseEntity<Object>(err, HttpStatus.CONFLICT);
		}
		registrationId.setVerified(true);
		registrationRepository.save(registrationId);
		SuccessResponse success = new SuccessResponse();
		success.setMessage("OTP verified successfully");
		success.setStatusCode("200");
		return new ResponseEntity<Object>(success, HttpStatus.OK);
	}

	boolean verify(String str, String str2) {
		if (str.equals(str2)) {
			return true;
		}
		return false;

	}

}
