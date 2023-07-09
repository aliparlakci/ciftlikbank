package com.parlakci.ciftlikbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaRepositories
@EnableFeignClients
@EnableAsync
public class CiftlikbankApplication {

	public static void main(String[] args) {
		SpringApplication.run(CiftlikbankApplication.class, args);
	}

}
