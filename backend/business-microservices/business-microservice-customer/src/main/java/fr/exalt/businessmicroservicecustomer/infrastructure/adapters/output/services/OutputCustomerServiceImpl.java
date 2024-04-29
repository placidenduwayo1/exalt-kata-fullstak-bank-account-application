package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.services;

import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.AddressNotFoundException;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.CustomerNotFoundException;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.ExceptionMsg;
import fr.exalt.businessmicroservicecustomer.domain.ports.output.OutputCustomerService;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.*;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.repositories.AddressRepository;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Transactional
@Slf4j
public class OutputCustomerServiceImpl implements OutputCustomerService {
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    public OutputCustomerServiceImpl(CustomerRepository customerRepository, AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public Request createCustomer(Customer customer, Address address) {
        AddressModel savedAddress = addressRepository.save(MapperService.fromTo(address));
        CustomerModel customerModel = customerRepository.save(MapperService.fromTo(customer));
        customerModel.setAddress(savedAddress);
        return Request.builder()
                .address(MapperService.fromTo(savedAddress))
                .customer(MapperService.fromTo(customerModel))
                .build();
    }

    @Override
    public Address getAddress(AddressDto dto) {
        AddressModel savedAddress = addressRepository.findByStreetNumAndStreetNameAndPoBoxAndCityAndCountry(
                dto.getStreetNum(), dto.getStreetName(), dto.getPoBox(), dto.getCity(), dto.getCountry());
        if (savedAddress != null) {
            return MapperService.fromTo(savedAddress);
        }
        return null;
    }

    @Override
    public Address getAddress(String addressId) throws AddressNotFoundException {
        AddressModel model = addressRepository.findById(addressId)
                .orElseThrow(()->new AddressNotFoundException(ExceptionMsg.ADDRESS_NOT_FOUND));
        return MapperService.fromTo(model);

    }

    @Override
    public Collection<Customer> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(model -> {
                    Address address = MapperService.fromTo(model.getAddress());
                    Customer customer = MapperService.fromTo(model);
                    customer.setAddress(address);
                    return customer;
                })
                .toList();
    }

    @Override
    public Customer getCustomer(String customerId) throws CustomerNotFoundException {
        CustomerModel model = customerRepository.findById(customerId).orElseThrow(
                () -> new CustomerNotFoundException(ExceptionMsg.CUSTOMER_NOT_FOUND));
        Address address = MapperService.fromTo(model.getAddress());
        Customer customer = MapperService.fromTo(model);
        customer.setAddress(address);
        return customer;
    }

    @Override
    public Address createAddress(Address address) {
        AddressModel model = addressRepository.save(MapperService.fromTo(address));
        return MapperService.fromTo(model);
    }

    @Override
    public Customer getCustomer(CustomerDto dto) {
        CustomerModel model = customerRepository.findByFirstnameAndLastnameAndState(
                dto.getFirstname(), dto.getLastname(), dto.getState());
        if (model != null) {
            return MapperService.fromTo(model);
        }
        return null;
    }

    @Override
    public Collection<Address> getAllAddresses() {
        return addressRepository.findAll().stream()
                .map(MapperService::fromTo).toList();
    }

    @Override
    public Request updateCustomer(Customer customer, Address address) {
        AddressModel model1 = addressRepository.save(MapperService.fromTo(address));
        CustomerModel model2 = customerRepository.save(MapperService.fromTo(customer));
        model2.setAddress(model1);
        return Request.builder()
                .address(MapperService.fromTo(model1))
                .customer(MapperService.fromTo(model2))
                .build();
    }

    @Override
    public Address updateAddress(Address address) {
        AddressModel addressModel = addressRepository.save(MapperService.fromTo(address));
        return MapperService.fromTo(addressModel);
    }
}
