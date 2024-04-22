package fr.exalt.businessmicroservicecustomer.domain.ports.input;

import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.RequestDto;

public interface InputCustomerService {
    Customer createCustomer(RequestDto requestDto);
}
