package com.demo.petclinic.appointment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AppointmentApplication {

	@Bean
	public RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
			@Override
			public boolean hasError(HttpStatus statusCode) {
				return false;
			}
		});
		return restTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(AppointmentApplication.class, args);
	}

}
