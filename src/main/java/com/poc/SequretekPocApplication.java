package com.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class })
@EnableFeignClients
@EntityScan
//@EnableJpaRepositories("com.poc.repository")
public class SequretekPocApplication {

	public static void main(String[] args) {
		SpringApplication.run(SequretekPocApplication.class, args);
	}

//	@Bean
//	public RestTemplate restTemplate() {
//		return new RestTemplate();
//	}

}
