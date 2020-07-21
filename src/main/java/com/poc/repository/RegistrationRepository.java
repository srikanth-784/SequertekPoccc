package com.poc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.reg.model.Registration;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Integer> {

	Registration getByRegistrationId(Integer registrationId);

}
