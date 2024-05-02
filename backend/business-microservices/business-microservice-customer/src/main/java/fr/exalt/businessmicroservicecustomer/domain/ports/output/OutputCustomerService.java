package fr.exalt.businessmicroservicecustomer.domain.ports.output;

import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.AddressNotFoundException;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.CustomerNotFoundException;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.AddressDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.CustomerDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.Request;

import java.util.Collection;

public interface OutputCustomerService {
    Request createCustomer(Customer customer, Address address);
   Address getAddress(AddressDto dto);
    Address getAddress(String addressId) throws AddressNotFoundException;
    Collection<Customer> getAllCustomers();
    Customer getCustomer(String customerId) throws CustomerNotFoundException;
    Address createAddress(Address address);
    Customer getCustomer(CustomerDto dto);
    Collection<Address> getAllAddresses();
    Request updateCustomer(Customer customer, Address address);
    Address updateAddress(Address address);

}
