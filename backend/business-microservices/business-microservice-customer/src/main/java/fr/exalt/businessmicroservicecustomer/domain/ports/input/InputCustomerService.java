package fr.exalt.businessmicroservicecustomer.domain.ports.input;

import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.AddressNotFoundException;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.CustomerNotFoundException;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.CustomerOneOrMoreFieldsInvalidException;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.CustomerStateInvalidException;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.AddressDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.Request;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.RequestDto;

import java.util.Collection;

public interface InputCustomerService {
    Request createCustomer(RequestDto requestDto) throws CustomerStateInvalidException, CustomerOneOrMoreFieldsInvalidException;
    Collection<Customer> getAllCustomers();
    Customer getCustomer(String customerId) throws CustomerNotFoundException;
    Address getAddress(AddressDto dto) throws AddressNotFoundException;
}
