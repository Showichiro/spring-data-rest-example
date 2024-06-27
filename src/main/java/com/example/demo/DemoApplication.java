package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import com.example.demo.validator.AppUserValidator;

@SpringBootApplication
public class DemoApplication implements RepositoryRestConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void configureValidatingRepositoryEventListener(
			ValidatingRepositoryEventListener v) {
		v.addValidator("beforeCreate", new AppUserValidator());
	}

}
