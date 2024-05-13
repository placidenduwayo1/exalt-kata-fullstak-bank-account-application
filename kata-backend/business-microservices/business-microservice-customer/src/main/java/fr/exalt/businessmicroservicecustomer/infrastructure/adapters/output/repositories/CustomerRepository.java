package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.repositories;

import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerModel, String> {
    CustomerModel findByFirstnameAndLastnameAndEmail(String firstname, String lastname, String email);
}
