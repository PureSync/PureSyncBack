package com.fcc.PureSync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PureSyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(PureSyncApplication.class, args);
	}

}
