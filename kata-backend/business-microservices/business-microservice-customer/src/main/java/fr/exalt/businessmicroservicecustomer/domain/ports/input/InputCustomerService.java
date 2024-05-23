package fr.exalt.businessmicroservicecustomer.domain.ports.input;

import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.*;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.AddressDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.CustomerSwitchStateDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.RequestDto;

import java.util.Collection;

public interface InputCustomerService {
    Customer createCustomer(RequestDto requestDto) throws CustomerStateInvalidException, CustomerOneOrMoreFieldsInvalidException,
            CustomerAlreadyExistsException, CustomerEmailInvalidException;

    Collection<Customer> getAllCustomers();

    Customer getCustomer(String customerId) throws CustomerNotFoundException;

    Address getAddress(AddressDto dto) throws AddressNotFoundException;
    Address getAddress(String addressId) throws AddressNotFoundException;

    Collection<Address> getAllAddresses();

    Customer updateCustomer(String customerId, RequestDto dto) throws CustomerStateInvalidException, CustomerOneOrMoreFieldsInvalidException,
            CustomerAlreadyExistsException, CustomerNotFoundException, CustomerEmailInvalidException;

    Address updateAddress(String addressId, AddressDto addressDto) throws AddressFieldsInvalidException, AddressNotFoundException;

    Customer switchCustomerBetweenActiveArchive(CustomerSwitchStateDto dto) throws CustomerNotFoundException, CustomerStateInvalidException,
            CustomerSameStateException, AddressNotFoundException;
}
