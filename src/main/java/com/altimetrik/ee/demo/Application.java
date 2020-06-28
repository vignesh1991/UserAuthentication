package com.altimetrik.ee.demo;

import com.altimetrik.ee.demo.security.JWTAuthorizationFilter;
import com.altimetrik.ee.demo.service.ComponentDetailsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/*
facing errors when connecting to mysql db so removing props
* @PropertySources({ @PropertySource(value = "classpath:db-config.properties"),
@PropertySource(value = "classpath:${k8s.db.env}.properties", ignoreResourceNotFound = true) })
* */
@EnableAsync
@EnableScheduling
@SpringBootApplication(scanBasePackages = { "com.altimetrik" })
@PropertySources({ @PropertySource(value = "classpath:db-config.properties"),
        @PropertySource(value = "classpath:${k8s.db.env}.properties", ignoreResourceNotFound = true) })
@EnableAutoConfiguration
public class Application {

	private static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(Application.class, args);
		context.getBean(ComponentDetailsService.class)
				.createComponentDetails(context.getEnvironment().getProperty("spring.application.name"));
	}


	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/user").permitAll()
					.anyRequest().authenticated();
		}
	}

}
