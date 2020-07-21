package com.poc.service;

import org.springframework.http.ResponseEntity;

import com.poc.request.RegistrationRequest;

public interface RegistrationService {

	public ResponseEntity<Object> saveDetails(RegistrationRequest registrationRequest);

	public ResponseEntity<Object> verifyOTP(RegistrationRequest registrationRequest);
}
