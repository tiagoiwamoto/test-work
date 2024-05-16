package org.example.testwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TestWorkApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestWorkApplication.class, args);
	}

}
