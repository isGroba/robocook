package com.cocina.robocook;

import com.cocina.robocook.repository.RobocookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RobocookApplication {

	public static void main(String[] args) {
		SpringApplication.run(RobocookApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(RobocookRepository receitaRepository) {
		return runner -> {

		};
	}

}
