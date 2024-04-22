package fr.exalt.businessmicroservicecustomer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BusinessMicroserviceCustomerApplication {

	public static void main(String[] args) {
		new SpringApplication(BusinessMicroserviceCustomerApplication.class)
				.run(args);
	}

}
