package fr.exalt.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayServiceApplication {

	public static void main(String[] args) {
		new SpringApplication(GatewayServiceApplication.class)
				.run(args);
	}

}
