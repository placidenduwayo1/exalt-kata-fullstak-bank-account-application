package fr.exalt.gatewayserviceproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayServiceProxyApplication {
	public static void main(String[] args) {
		new SpringApplication(GatewayServiceProxyApplication.class)
				.run(args);
	}
}
