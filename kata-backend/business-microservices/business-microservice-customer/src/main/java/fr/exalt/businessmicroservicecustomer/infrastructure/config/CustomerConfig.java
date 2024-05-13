package fr.exalt.businessmicroservicecustomer.infrastructure.config;

import fr.exalt.businessmicroservicecustomer.domain.ports.output.OutputCustomerService;
import fr.exalt.businessmicroservicecustomer.domain.usecase.InputCustomerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CustomerConfig {
    @Bean
    public InputCustomerImpl config(OutputCustomerService outputCustomerService){
        return new InputCustomerImpl(outputCustomerService);
    }
}
