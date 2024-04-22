package fr.exalt.registrationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RegistrationServiceApplication {

	public static void main(String[] args) {
		new SpringApplication(RegistrationServiceApplication.class)
				.run(args);
	}

}
