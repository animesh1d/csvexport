package com.example.project.springbootrestapiuserdetails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableHystrixDashboard
@EnableHystrix
@EnableCircuitBreaker
public class SpringBootRestApiUserDetailsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApiUserDetailsApplication.class, args);
	}

}
