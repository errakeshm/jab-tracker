package com.tracker.cowin.batch.configurations;


import java.time.Duration;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApiConfiguration {
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		RestTemplate restTemplate = builder
				.setReadTimeout(Duration.ofSeconds(60))
				.setConnectTimeout(Duration.ofSeconds(30))
				.build();
		// Setting the user agent
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		CloseableHttpClient httpClient
	      = HttpClients.custom()
	        .setUserAgent("Application")
	        .build();
		requestFactory.setHttpClient(httpClient);
		restTemplate.setRequestFactory(requestFactory);
		return restTemplate;
	}
}
