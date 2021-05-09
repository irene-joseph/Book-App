package com.stackroute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class BookServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
	}

}
