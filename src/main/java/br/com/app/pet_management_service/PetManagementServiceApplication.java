package br.com.app.pet_management_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = "br.com.app.pet_management_service")
@EnableJpaRepositories(basePackages = "br.com.app.pet_management_service")
@EntityScan(basePackages = "br.com.app.pet_management_service")
@SpringBootApplication
public class PetManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetManagementServiceApplication.class, args);
	}

}
