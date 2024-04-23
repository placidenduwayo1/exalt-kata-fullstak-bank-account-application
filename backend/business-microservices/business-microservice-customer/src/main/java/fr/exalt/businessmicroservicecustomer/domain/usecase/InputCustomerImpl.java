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
import java.util.List;
import java.util.UUID;
@Slf4j
public class InputCustomerImpl implements InputCustomerService {
    private final OutputCustomerService outputCustomerService;

    public InputCustomerImpl(OutputCustomerService outputCustomerService) {
        this.outputCustomerService = outputCustomerService;
    }

    @Override
    public Request createCustomer(RequestDto requestDto) throws CustomerStateInvalidException, CustomerOneOrMoreFieldsInvalidException {
        CustomerValidator.formatter(requestDto);
        if (!CustomerValidator.isValidCustomerState(requestDto.getCustomerDto().getState())) {
            throw new CustomerStateInvalidException(ExceptionMsg.CUSTOMER_STATE_INVALID);
        }

        if (CustomerValidator.invalidRequest(requestDto)) {
            throw new CustomerOneOrMoreFieldsInvalidException(ExceptionMsg.CUSTOMER_FIELD_INVALID);
        }
        Address mappedAddress = MapperService.fromTo(requestDto.getAddressDto());
        mappedAddress.setAddressId(UUID.randomUUID().toString());
        Address savedAddress = getAddress(requestDto.getAddressDto());
        if(savedAddress==null){
            savedAddress = outputCustomerService.createAddress(mappedAddress);
        }
        Customer customer = MapperService.fromTo(requestDto.getCustomerDto());
        customer.setCustomerId(UUID.randomUUID().toString());
        customer.setCreatedAt(Timestamp.from(Instant.now()).toString());
        Request request = outputCustomerService.createCustomer(customer, savedAddress);
        request.setAddress(savedAddress);
        log.error(String.format("saved %s",request));

        return request;
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
}
