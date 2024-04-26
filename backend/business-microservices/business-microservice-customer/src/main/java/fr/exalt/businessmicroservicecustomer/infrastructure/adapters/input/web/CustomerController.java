package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.input.web;

import fr.exalt.businessmicroservicecustomer.domain.entities.BankAccount;
import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.*;
import fr.exalt.businessmicroservicecustomer.domain.ports.input.InputCustomerService;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.AddressDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.RequestDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/api-customer")
@AllArgsConstructor
public class CustomerController {
    private final InputCustomerService inputCustomerService;

    @PostMapping(value = "/customers")
    public Customer createCustomer(@RequestBody RequestDto dto) throws CustomerStateInvalidException,
            CustomerOneOrMoreFieldsInvalidException, CustomerAlreadyExistsException {
        return inputCustomerService.createCustomer(dto);
    }

    @GetMapping(value = "/customers")
    public Collection<Customer> getAllCustomers() {
        return inputCustomerService.getAllCustomers();
    }

    @GetMapping(value = "/addresses")
    public Collection<Address> getAllAddresses() {
        return inputCustomerService.getAllAddresses();
    }
    @PutMapping(value = "/customers/{customerId}")
    public Customer updateCustomer(@PathVariable(name = "customerId") String customerId, @RequestBody RequestDto dto) throws
            CustomerStateInvalidException, CustomerOneOrMoreFieldsInvalidException, CustomerNotFoundException,
            CustomerAlreadyExistsException {
        return inputCustomerService.updateCustomer(customerId, dto);
    }
    @PutMapping(value = "/addresses/{addressId}")
    public Address updateAddress(@PathVariable(name = "addressId") String addressId, @RequestBody AddressDto addressDto) throws
            AddressFieldsInvalidException, AddressNotFoundException {
        return inputCustomerService.updateAddress(addressId, addressDto);
    }
    @GetMapping(value = "/customers/{customerId}")
    public Customer getCustomer(@PathVariable(name = "customerId") String customerId) throws CustomerNotFoundException {
        return inputCustomerService.getCustomer(customerId);
    }
    @GetMapping(value = "/customers/{customerId}/accounts")
    public Collection<BankAccount> loadRemoteAccountsOfGivenCustomer(@PathVariable(name = "customerId") String customerId){
        return inputCustomerService.loadRemoteAccountsOfCustomer(customerId);
    }
 }
