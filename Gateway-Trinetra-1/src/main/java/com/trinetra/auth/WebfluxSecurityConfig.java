package com.trinetra.auth;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.session.WebSessionManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trinetra.exception.RestAccessDeniedHandler;
import com.trinetra.service.UserDetailsService;

import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@Configuration
@NoArgsConstructor
public class WebfluxSecurityConfig {
   
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    	return http
		        .authorizeExchange().pathMatchers("/api/v1/auth/**").permitAll()
		        .anyExchange().permitAll()
		        .and().securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
		        .csrf().disable()
		        .build();
    }
    
	@Bean
	public ReactiveAuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
		UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(
				userDetailsService);
		authenticationManager.setPasswordEncoder(passwordEncoder());
		return authenticationManager;
	}
	
	@Bean
	public WebSessionManager webSessionManager() {
	    return exchange -> Mono.empty();
	}
	
	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder rlb, JwtAuthenticationFilter authorizationHeaderFilter) {

		return rlb.routes()
				.route(p -> p.path("/api/v1/logincheck/**","/api/v1/analytics/**","/api/v1/devicehealth/**")
						.filters(f -> f.filter(authorizationHeaderFilter.apply(new JwtAuthenticationFilter.Config())))
						.uri("lb://trinetra-analytics-service"))
				
				.route(p -> p.path("/api/v1/reports/**","/api/v1/alert/report/emergencyAlert")
						.filters(f -> f.filter(authorizationHeaderFilter.apply(new JwtAuthenticationFilter.Config())))
						.uri("lb://trinetra-reports-service"))
				.build();
	}

    @Bean
    public RestAccessDeniedHandler restAccessDeniedHandler() {
        return new RestAccessDeniedHandler();
    }

    
    @Bean
    public ServletContext servletContext() {
    	return new MockServletContext();
    }
    
    @Bean
    @Primary
    public MethodSecurityMetadataSource primaryMethodMetadataSource(@Qualifier("methodMetadataSource") MethodSecurityMetadataSource methodMetadataSource) {
        return methodMetadataSource;
    }
    
    @Bean
	public RestTemplate restTemplate() {
		return new RestTemplateBuilder().build();
	}
    
    @Bean
    @Scope("singleton")
    public ObjectMapper objectMapper() {
    	return new ObjectMapper();
    }
}
