package com.trinetra.auth;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;


@Component
public class JwtAuthenticationFilter implements GatewayFilterFactory<JwtAuthenticationFilter.Config> {

	public static class Config {
		public Config() {
			
		}
	}
	
	@Override
	public Class<Config> getConfigClass() {
		return Config.class;
	}
	
	@Override
	public Config newConfig() {
		return new Config();
	}
	
	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			return chain.filter(exchange);
		};
	}
	
	@Bean
	public ServerCodecConfigurer serverCodecConfigurer() {
		   return ServerCodecConfigurer.create();
	}

}
