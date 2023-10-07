package com.example.cs309;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.cs309.domain"})
public class  Cs309Application {

	public static void main(String[] args) {
		SpringApplication.run(Cs309Application.class, args);
	}

}
