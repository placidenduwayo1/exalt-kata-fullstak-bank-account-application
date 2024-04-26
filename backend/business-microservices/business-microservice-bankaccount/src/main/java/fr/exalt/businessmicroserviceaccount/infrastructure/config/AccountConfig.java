package fr.exalt.businessmicroserviceaccount.infrastructure.config;

import fr.exalt.businessmicroserviceaccount.domain.ports.output.OutputAccountService;
import fr.exalt.businessmicroserviceaccount.domain.usecase.InputAccountServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AccountConfig {
    @Bean
    public InputAccountServiceImpl config(OutputAccountService outputAccountService){
        return new InputAccountServiceImpl(outputAccountService);
    }
}
