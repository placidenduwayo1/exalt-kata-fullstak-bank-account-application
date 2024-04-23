package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.repositories;

import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.security.SecureRandom;

public interface CustomerRepository extends JpaRepository<CustomerModel, String> {
    CustomerModel findByFirstnameAndLastnameAndState(String firstname, String lastname, String state);
}
