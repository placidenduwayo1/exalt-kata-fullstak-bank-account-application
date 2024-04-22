package fr.exalt.businessmicroservicecustomer.domain.usecase;

import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.CustomerNotFoundException;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.CustomerOneOrMoreFieldsInvalidException;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.CustomerStateInvalidException;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.ExceptionMsg;
import fr.exalt.businessmicroservicecustomer.domain.ports.input.InputCustomerService;
import fr.exalt.businessmicroservicecustomer.domain.ports.output.OutputCustomerService;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.RequestDto;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

public class InputCustomerImpl implements InputCustomerService {
    private final MapperService mapperService;
    private final OutputCustomerService outputCustomerService;

    public InputCustomerImpl(MapperService mapperService, OutputCustomerService outputCustomerService) {
        this.mapperService = mapperService;
        this.outputCustomerService = outputCustomerService;
    }

    @Override
    public Customer createCustomer(RequestDto requestDto) throws CustomerStateInvalidException, CustomerOneOrMoreFieldsInvalidException {
        CustomerValidator.formatter(requestDto);
        if (! CustomerValidator.isValidCustomerState(requestDto.getCustomerDto().getState())){
            throw new CustomerStateInvalidException(ExceptionMsg.CUSTOMER_STATE_INVALID);
        }

        if(CustomerValidator.invalidRequest(requestDto)){
            throw new CustomerOneOrMoreFieldsInvalidException(ExceptionMsg.CUSTOMER_FIELD_INVALID);
        }
        Address address = mapperService.to(requestDto.getAddressDto());
        address.setAddressId(UUID.randomUUID().toString());
        Customer customer = mapperService.to(requestDto.getCustomerDto());
        customer.setCustomerId(UUID.randomUUID().toString());
        customer.setAddressId(address.getAddressId());
        customer.setAddress(address);
        customer.setCreatedAt(Timestamp.from(Instant.now()).toString());

        return outputCustomerService.createCustomer(customer);
    }

    @Override
    public Collection<Customer> getAllCustomers() {
        return outputCustomerService.getAllCustomers();
    }

    @Override
    public Customer getCustomer(String customerId) throws CustomerNotFoundException {
        return outputCustomerService.getCustomer(customerId);
    }
}
