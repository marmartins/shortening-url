package com.marcsystem.shorturl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class ShortUrlServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortUrlServicesApplication.class, args);
	}
}
