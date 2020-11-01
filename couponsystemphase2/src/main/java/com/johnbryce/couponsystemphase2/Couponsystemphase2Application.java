package com.johnbryce.couponsystemphase2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class Couponsystemphase2Application {

	public static void main(String[] args) {
		SpringApplication.run(Couponsystemphase2Application.class, args);
		System.out.println("IOC container was loaded");
	}

}
