package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.input;

import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.CustomerAlreadyExistsException;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.CustomerNotFoundException;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.CustomerOneOrMoreFieldsInvalidException;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.CustomerStateInvalidException;
import fr.exalt.businessmicroservicecustomer.domain.ports.input.InputCustomerService;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.AddressDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.Request;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.RequestDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "api-customer")
@AllArgsConstructor
public class CustomerController {
    private final InputCustomerService inputCustomerService;

    @PostMapping(value = "/customers")
    public Request createCustomer(@RequestBody RequestDto dto) throws CustomerStateInvalidException,
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
    public Request updateCustomer(@PathVariable(name = "customerId") String customerId, @RequestBody RequestDto dto) throws
            CustomerStateInvalidException, CustomerOneOrMoreFieldsInvalidException, CustomerNotFoundException,
            CustomerAlreadyExistsException {
        return inputCustomerService.updateCustomer(customerId, dto);
    }
    @PutMapping(value = "/addresses/{addressId}")
    public Address updateAddress(@PathVariable(name = "addressId") String addressId, @RequestBody AddressDto addressDto){
        return inputCustomerService.updateAddress(addressId, addressDto);
    }
}
