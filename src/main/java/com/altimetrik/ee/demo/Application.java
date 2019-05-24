package com.altimetrik.ee.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.altimetrik.ee.demo.service.ComponentDetailsService;

@EnableAsync
@EnableScheduling
@SpringBootApplication(scanBasePackages = { "com.altimetrik" })
@PropertySource("classpath:db-config.properties")
public class Application {

	private static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(Application.class, args);
		context.getBean(ComponentDetailsService.class)
				.createComponentDetails(context.getEnvironment().getProperty("spring.application.name"));
	}

}
