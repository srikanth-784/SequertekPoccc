package com.poc.controller;

import static com.poc.constants.UrlConstants.SET;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poc.request.RegistrationRequest;
import com.poc.response.ErrorResponse;
import com.poc.service.RegistrationService;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
	@Autowired
	private RegistrationService registrationService;
	Logger logger = Logger.getLogger(RegistrationController.class);

	@PostMapping(SET)
	public ResponseEntity<Object> saveDetails(@RequestBody RegistrationRequest registrationRequest) {
		logger.debug("Incoming request : " + registrationRequest);
		try {

			if (registrationRequest == null || registrationRequest.getNotificationType() == null
					|| registrationRequest.getNotificationType().isEmpty()) {
				logger.error("Invalid request");
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setMessage("Invalid Request");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			return registrationService.saveDetails(registrationRequest);

		} catch (Exception e) {

			logger.error("Exception caught : " + e.getMessage());
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setMessage("Exception caught!");
			error.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}

	}

	@PostMapping("/verify")
	public ResponseEntity<Object> otpVerification(@RequestBody RegistrationRequest registrationRequest) {

		return registrationService.verifyOTP(registrationRequest);
	}

}
