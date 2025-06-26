package com.war_thermometer;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.war_thermometer.services")
public class WarThermometerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarThermometerApplication.class, args);
	}

}
