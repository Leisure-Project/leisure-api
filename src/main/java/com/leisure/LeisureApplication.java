package com.leisure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LeisureApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeisureApplication.class, args);
	}

}
