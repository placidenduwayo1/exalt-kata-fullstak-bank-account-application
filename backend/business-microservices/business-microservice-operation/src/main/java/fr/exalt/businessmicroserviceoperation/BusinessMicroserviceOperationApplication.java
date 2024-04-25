package fr.exalt.businessmicroserviceoperation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BusinessMicroserviceOperationApplication {
	public static void main(String[] args) {
		new SpringApplication(BusinessMicroserviceOperationApplication.class)
				.run(args);
	}
}
