package com.poc.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import feign.Headers;
import feign.Response;

@FeignClient(name = "SendMail", url = "${spring.feign.client.url.TestUrl}/mail/set")
public interface FeignService {
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
	@Headers("Content-Type: application/json")
	public Response getEmails(@RequestBody String str);
}
