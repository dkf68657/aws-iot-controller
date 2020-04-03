package com.aws.iot.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class AwsIoTController {

	public static void main(String[] args) {
		SpringApplication.run(AwsIoTController.class, args);
	}
}

