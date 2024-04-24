package fr.exalt.businessmicroserviceaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BusinessMicroserviceAccountApplication {
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(BusinessMicroserviceAccountApplication.class);
		application.run(args);
	}
}
