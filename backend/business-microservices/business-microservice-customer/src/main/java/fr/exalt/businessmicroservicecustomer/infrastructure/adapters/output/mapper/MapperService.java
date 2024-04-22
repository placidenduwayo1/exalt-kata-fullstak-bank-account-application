package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.mapper;

import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.AddressDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.AddressModel;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.CustomerDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.CustomerModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapperService {
    Customer to(CustomerDto dto);
    @Mapping(target = "customerId", ignore = true)
    CustomerDto toDto(Customer customer);
    Address to(AddressDto dto);
    CustomerModel to(Customer customer);
    Customer to(CustomerModel model);
    AddressModel to(Address address);
}
