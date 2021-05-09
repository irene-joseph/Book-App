package com.stackroute.apigatewayservice.filters;

import com.stackroute.apigatewayservice.filters.filters.ErrorFilter;
import com.stackroute.apigatewayservice.filters.filters.PostFilter;
import com.stackroute.apigatewayservice.filters.filters.PreFilter;
import com.stackroute.apigatewayservice.filters.filters.RouteFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@SpringBootApplication
public class ApiGatewayServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayServiceApplication.class, args);}
		@Bean
		public PreFilter preFilter() {
			return new PreFilter();
		}

		@Bean
		public PostFilter postFilter() {
			return new PostFilter();
		}

		@Bean
		public ErrorFilter errorFilter() {
			return new ErrorFilter();
		}

		@Bean
		public RouteFilter routeFilter() {
			return new RouteFilter();
		}
}
