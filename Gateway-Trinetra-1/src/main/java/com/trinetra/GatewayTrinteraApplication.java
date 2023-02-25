package com.trinetra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.web.reactive.config.EnableWebFlux;

import com.trinetra.auth.JwtAuthenticationFilter;
import com.trinetra.auth.JwtUtil;
import com.trinetra.repo.UserRepository;
import com.trinetra.service.UserDetailsService;


@SpringBootApplication(scanBasePackageClasses = {JwtUtil.class,UserDetailsService.class,JwtAuthenticationFilter.class}, scanBasePackages = {"com.trinetra","com.trinetra.config","com.trinetra.auth","com.trinetra.service","com.trinetra.mailer","com.trinetra.utils"})
@ComponentScan(useDefaultFilters = true, basePackages = {"com.trinetra","com.trinetra.config","com.trinetra.auth","com.trinetra.service","com.trinetra.mailer","com.trinetra.utils"},basePackageClasses = {JwtUtil.class,UserDetailsService.class,JwtAuthenticationFilter.class})
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableEurekaClient
@EnableWebFlux
public class GatewayTrinteraApplication extends SpringBootServletInitializer{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GatewayTrinteraApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(GatewayTrinteraApplication.class, args);
	}
}
