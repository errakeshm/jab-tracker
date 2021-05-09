package com.tracker.cowin;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableBatchProcessing
@ImportResource("classpath:beans.xml")
public class CowinApplication {

	public static void main(String[] args) {
		SpringApplication.run(CowinApplication.class, args);
	}
}
