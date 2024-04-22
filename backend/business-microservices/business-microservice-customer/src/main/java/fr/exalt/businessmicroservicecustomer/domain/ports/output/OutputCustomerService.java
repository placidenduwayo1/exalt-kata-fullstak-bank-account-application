package fr.exalt.businessmicroservicecustomer.domain.ports.output;

import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.CustomerNotFoundException;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.RequestDto;

import java.util.Collection;

public interface OutputCustomerService {
    Customer createCustomer(Customer customer);
    Collection<Customer> getAllCustomers();
    Customer getCustomer(String customerId) throws CustomerNotFoundException;
}
