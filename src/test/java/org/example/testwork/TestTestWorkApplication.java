package org.example.testwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestTestWorkApplication {

	public static void main(String[] args) {
		SpringApplication.from(TestWorkApplication::main).with(TestTestWorkApplication.class).run(args);
	}

}
