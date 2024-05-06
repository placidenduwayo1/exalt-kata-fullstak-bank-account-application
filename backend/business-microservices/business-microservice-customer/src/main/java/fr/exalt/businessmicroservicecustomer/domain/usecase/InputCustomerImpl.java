package fr.exalt.businessmicroservicecustomer.domain.usecase;

import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.*;
import fr.exalt.businessmicroservicecustomer.domain.ports.input.InputCustomerService;
import fr.exalt.businessmicroservicecustomer.domain.ports.output.OutputCustomerService;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.AddressDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.CustomerArchiveDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.RequestDto;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

public class InputCustomerImpl implements InputCustomerService {
    private final OutputCustomerService outputCustomerService;
    private static final String INITIAL_STATE="active";

    public InputCustomerImpl(OutputCustomerService outputCustomerService) {
        this.outputCustomerService = outputCustomerService;
    }

    @Override
    public Customer createCustomer(RequestDto requestDto) throws CustomerStateInvalidException,
            CustomerOneOrMoreFieldsInvalidException, CustomerAlreadyExistsException, CustomerEmailInvalidException {

        validateCustomer(requestDto);
        Address mappedAddress = MapperService.fromTo(requestDto.getAddressDto());
        mappedAddress.setAddressId(UUID.randomUUID().toString());
        Address savedAddress = getAddress(requestDto.getAddressDto());
        Customer customer = MapperService.fromTo(requestDto.getCustomerDto());
        if (savedAddress == null) {
            savedAddress = outputCustomerService.createAddress(mappedAddress);
            customer.setAddress(savedAddress);
        }

        customer.setAddress(savedAddress);
        customer.setCustomerId(UUID.randomUUID().toString());
        customer.setCreatedAt(Timestamp.from(Instant.now()).toString());
        customer.setState(INITIAL_STATE);
        outputCustomerService.createCustomer(customer, savedAddress);
        return customer;
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
    public Address getAddress(AddressDto dto) {
        return outputCustomerService.getAddress(dto);
    }

    @Override
    public Address getAddress(String addressId) throws AddressNotFoundException {
        return outputCustomerService.getAddress(addressId);
    }

    @Override
    public Collection<Address> getAllAddresses() {
        return outputCustomerService.getAllAddresses();
    }

    @Override
    public Customer updateCustomer(String customerId, RequestDto requestDto) throws CustomerStateInvalidException,
            CustomerOneOrMoreFieldsInvalidException, CustomerAlreadyExistsException, CustomerNotFoundException, CustomerEmailInvalidException {

        validateCustomer(requestDto);
        Customer customer = getCustomer(customerId);
        Address address = getAddress(requestDto.getAddressDto());
        if (address == null) {
            address = outputCustomerService.createAddress(MapperService.fromTo(requestDto.getAddressDto()));
        }
        customer.setAddress(address);
        customer.setFirstname(requestDto.getCustomerDto().getFirstname());
        customer.setLastname(requestDto.getCustomerDto().getLastname());
        outputCustomerService.updateCustomer(customer, address);
        return customer;

    }

    @Override
    public Address updateAddress(String addressId, AddressDto dto) throws AddressFieldsInvalidException, AddressNotFoundException {
        CustomerValidators.formatter(dto);
        if (CustomerValidators.invalidAddressDto(dto)) {
            throw new AddressFieldsInvalidException(ExceptionMsg.ADDRESS_FIELDS);
        }
        Address address = getAddress(addressId);
        address.setStreetNum(dto.getStreetNum());
        address.setStreetName(dto.getStreetName());
        address.setPoBox(dto.getPoBox());
        address.setCity(dto.getCity());
        address.setCountry(dto.getCountry());

        // call output adapter to register updated address
        return outputCustomerService.updateAddress(address);
    }

    @Override
    public Customer archiveCustomer(CustomerArchiveDto dto) throws CustomerNotFoundException, CustomerStateInvalidException, CustomerAlreadyArchivedException {
        if(!CustomerValidators.isValidCustomerState(dto.getState())){
            throw new CustomerStateInvalidException(ExceptionMsg.CUSTOMER_STATE_INVALID);
        }

        Customer customer = getCustomer(dto.getCustomerId());
        if(customer.getState().equals("archive"))
            throw new CustomerAlreadyArchivedException(ExceptionMsg.CUSTOMER_ALREADY_ARCHIVED);
        customer.setState(dto.getState());
        return outputCustomerService.archiveCustomer(customer);
    }

    private void validateCustomer(RequestDto requestDto) throws
            CustomerOneOrMoreFieldsInvalidException, CustomerAlreadyExistsException, CustomerEmailInvalidException {

        CustomerValidators.formatter(requestDto);
        if (CustomerValidators.invalidRequest(requestDto)) {
            throw new CustomerOneOrMoreFieldsInvalidException(ExceptionMsg.CUSTOMER_FIELD_INVALID);
        }
        if (!CustomerValidators.isValidEmail(requestDto.getCustomerDto().getEmail())) {
            throw new CustomerEmailInvalidException(ExceptionMsg.CUSTOMER_EMAIL_INVALID);
        }

        Customer savedCustomer = outputCustomerService.getCustomer(requestDto.getCustomerDto());
        if (savedCustomer != null) {
            throw new CustomerAlreadyExistsException(ExceptionMsg.CUSTOMER_ALREADY_EXISTS);
        }
    }
}
