package com.tcs.ifact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//enable Spring Boot caching
@EnableCaching
public class IfactApplication {

	public static void main(String[] args) {
		SpringApplication.run(IfactApplication.class, args);
	}
}
