package fr.exalt.microservicesconfigurationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class MicroservicesConfigurationServerApplication {

	public static void main(String[] args) {
		new SpringApplication(MicroservicesConfigurationServerApplication.class)
				.run(args);
	}

}
