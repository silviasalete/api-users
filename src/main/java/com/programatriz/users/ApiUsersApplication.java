package com.programatriz.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ApiUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiUsersApplication.class, args);
	}

}
