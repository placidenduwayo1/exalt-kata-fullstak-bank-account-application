package fr.exalt.businessmicroservicecustomer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BusinessMicroserviceCustomerApplication {

	public static void main(String[] args) {
		new SpringApplication(BusinessMicroserviceCustomerApplication.class)
				.run(args);
	}

}
