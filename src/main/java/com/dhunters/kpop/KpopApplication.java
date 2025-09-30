package com.dhunters.kpop;

import com.dhunters.kpop.jwt.config.JwtIssuerProperties;
import com.dhunters.kpop.jwt.config.JwtVerifierProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({JwtIssuerProperties.class, JwtVerifierProperties.class})
public class KpopApplication {

	public static void main(String[] args) {
		SpringApplication.run(KpopApplication.class, args);
	}

}
