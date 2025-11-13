package br.mackenzie.mackmusic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class MackmusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(MackmusicApplication.class, args);
	}

}
