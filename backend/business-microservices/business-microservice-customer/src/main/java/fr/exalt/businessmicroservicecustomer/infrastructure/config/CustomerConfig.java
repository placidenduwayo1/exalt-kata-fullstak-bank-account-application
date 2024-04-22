package fr.exalt.businessmicroservicecustomer.infrastructure.config;

import fr.exalt.businessmicroservicecustomer.domain.ports.output.OutputCustomerService;
import fr.exalt.businessmicroservicecustomer.domain.usecase.InputCustomerImpl;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.mapper.MapperService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CustomerConfig {
    @Bean
    public InputCustomerImpl config(MapperService mapperService, OutputCustomerService outputCustomerService){
        return new InputCustomerImpl(mapperService, outputCustomerService);
    }
}
