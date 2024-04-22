package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.services;

import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.CustomerNotFoundException;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.ExceptionMsg;
import fr.exalt.businessmicroservicecustomer.domain.ports.output.OutputCustomerService;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.CustomerModel;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
@RequiredArgsConstructor
@Transactional
public class OutputCustomerServiceImpl implements OutputCustomerService {
    private final CustomerRepository customerRepository;
    private final MapperService mapperService;
    @Override
    public Customer createCustomer(Customer customer) {
        CustomerModel model = customerRepository.save(mapperService.to(customer));
        return mapperService.to(model);
    }

    @Override
    public Collection<Customer> getAllCustomers() {
        Collection<CustomerModel> models = customerRepository.findAll();
        return models.stream().map(mapperService::to)
                .toList();
    }

    @Override
    public Customer getCustomer(String customerId) throws CustomerNotFoundException {
        CustomerModel model = customerRepository.findById(customerId).orElseThrow(
                ()-> new CustomerNotFoundException(ExceptionMsg.CUSTOMER_NOT_FOUND));
        return mapperService.to(model);
    }
}
