package com.trinetra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AnalyticsTrinetraApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalyticsTrinetraApplication.class, args);
	}

}
