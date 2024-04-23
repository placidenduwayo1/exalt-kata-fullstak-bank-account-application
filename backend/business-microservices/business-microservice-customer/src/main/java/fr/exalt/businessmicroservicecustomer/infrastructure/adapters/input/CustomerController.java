package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.input;

import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.CustomerOneOrMoreFieldsInvalidException;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.CustomerStateInvalidException;
import fr.exalt.businessmicroservicecustomer.domain.ports.input.InputCustomerService;
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
    public Request createCustomer(@RequestBody RequestDto dto) throws CustomerStateInvalidException, CustomerOneOrMoreFieldsInvalidException {
        return inputCustomerService.createCustomer(dto);
    }
    @GetMapping(value = "/customers")
    public Collection<Customer> getAllCustomers(){
        return inputCustomerService.getAllCustomers();
    }
}
