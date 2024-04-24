package fr.exalt.businessmicroservicecustomer.domain.usecase;

import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.*;
import fr.exalt.businessmicroservicecustomer.domain.ports.input.InputCustomerService;
import fr.exalt.businessmicroservicecustomer.domain.ports.output.OutputCustomerService;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.AddressDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.Request;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.RequestDto;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.UUID;
@Slf4j
public class InputCustomerImpl implements InputCustomerService {
    private final OutputCustomerService outputCustomerService;

    public InputCustomerImpl(OutputCustomerService outputCustomerService) {
        this.outputCustomerService = outputCustomerService;
    }

    @Override
    public Customer createCustomer(RequestDto requestDto) throws CustomerStateInvalidException,
            CustomerOneOrMoreFieldsInvalidException, CustomerAlreadyExistsException {

        validateCustomer(requestDto);
        Address mappedAddress = MapperService.fromTo(requestDto.getAddressDto());
        mappedAddress.setAddressId(UUID.randomUUID().toString());
        Address savedAddress = getAddress(requestDto.getAddressDto());
        Customer customer = MapperService.fromTo(requestDto.getCustomerDto());
        log.error("address 1{}",savedAddress);
        if(savedAddress==null){
            savedAddress = outputCustomerService.createAddress(mappedAddress);
            customer.setAddress(savedAddress);
        }
        customer.setCustomerId(UUID.randomUUID().toString());
        customer.setCreatedAt(Timestamp.from(Instant.now()).toString());
        customer.setAddress(mappedAddress);
        Request request = outputCustomerService.createCustomer(customer, savedAddress);
        log.error("request 1{}", request);
        request.setAddress(savedAddress);
        request.setCustomer(customer);
        log.error("request 2{}", request);
        return request.getCustomer();
    }

    @Override
    public Collection<Customer> getAllCustomers() {
     return outputCustomerService.getAllCustomers();
    }

    @Override
    public Customer getCustomer(String customerId) throws CustomerNotFoundException {
        return outputCustomerService.getCustomer(customerId);
    }

    @Override
    public Address getAddress(AddressDto dto)  {
       return outputCustomerService.getAddress(dto);
    }

    @Override
    public Collection<Address> getAllAddresses() {
        return outputCustomerService.getAllAddresses();
    }

    @Override
    public Customer updateCustomer(String customerId, RequestDto requestDto) throws CustomerStateInvalidException,
            CustomerOneOrMoreFieldsInvalidException, CustomerAlreadyExistsException, CustomerNotFoundException {
        validateCustomer(requestDto);

        Customer customer = getCustomer(customerId);
        Address address = getAddress(requestDto.getAddressDto());
        if(address==null){
            address= outputCustomerService.createAddress(MapperService.fromTo(requestDto.getAddressDto()));
        }
        customer.setAddress(address);
        customer.setFirstname(requestDto.getCustomerDto().getFirstname());
        customer.setLastname(requestDto.getCustomerDto().getLastname());
        customer.setState(requestDto.getCustomerDto().getState());

        Request request = outputCustomerService.updateCustomer(customer, address);
        request.setCustomer(customer);
        request.setAddress(address);
        return request.getCustomer();

    }

    @Override
    public Address updateAddress(String addressId, AddressDto addressDto) {

        return null;
    }

    private void validateCustomer(RequestDto requestDto) throws CustomerStateInvalidException,
            CustomerOneOrMoreFieldsInvalidException, CustomerAlreadyExistsException {

        CustomerValidator.formatter(requestDto);

        if (!CustomerValidator.isValidCustomerState(requestDto.getCustomerDto().getState())) {
            throw new CustomerStateInvalidException(ExceptionMsg.CUSTOMER_STATE_INVALID);
        }
        if (CustomerValidator.invalidRequest(requestDto)) {
            throw new CustomerOneOrMoreFieldsInvalidException(ExceptionMsg.CUSTOMER_FIELD_INVALID);
        }
        Customer savedCustomer = outputCustomerService.getCustomer(requestDto.getCustomerDto());
        if(savedCustomer!=null){
            throw new CustomerAlreadyExistsException(ExceptionMsg.CUSTOMER_ALREADY_EXISTS);
        }
    }

}
