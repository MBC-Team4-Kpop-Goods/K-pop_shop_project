package com.dhunters.kpop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KpopApplication {

	public static void main(String[] args) {
		SpringApplication.run(KpopApplication.class, args);
	}

}
