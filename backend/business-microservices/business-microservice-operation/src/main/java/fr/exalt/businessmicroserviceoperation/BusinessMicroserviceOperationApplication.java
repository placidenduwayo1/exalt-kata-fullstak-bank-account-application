package fr.exalt.businessmicroserviceoperation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BusinessMicroserviceOperationApplication {
	public static void main(String[] args) {
		new SpringApplication(BusinessMicroserviceOperationApplication.class)
				.run(args);
	}
}
