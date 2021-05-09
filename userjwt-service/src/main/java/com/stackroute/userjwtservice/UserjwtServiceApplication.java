package com.stackroute.userjwtservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.stackroute.userjwtservice.config.JWTFilter;
import org.springframework.web.client.RestTemplate;

//configuration class that declares one or more @Bean methods and also triggers auto-configuration and component scanning
//Spring Boot Application which will be used for the purpose of User Authentication
@EnableEurekaClient
@SpringBootApplication
public class UserjwtServiceApplication {
	
	    // Bean of JWT token for validating the JWT provided in header
		@Bean
		public FilterRegistrationBean jwtFilter() {
			
			FilterRegistrationBean registrationBean = new FilterRegistrationBean();
			registrationBean.setFilter(new JWTFilter());
			registrationBean.addUrlPatterns("/api/user/*");
			return registrationBean;
			
		}

		@Bean
		public RestTemplate getRestTemplate() {
			return new RestTemplate();
		}

	public static void main(String[] args) {
		SpringApplication.run(UserjwtServiceApplication.class, args);
		
}
}
