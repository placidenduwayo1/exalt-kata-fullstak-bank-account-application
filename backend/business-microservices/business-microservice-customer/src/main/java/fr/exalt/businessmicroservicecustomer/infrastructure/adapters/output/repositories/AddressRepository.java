package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.repositories;

import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<AddressModel, String> {
    List<AddressModel> findByStreetNumAndStreetNameAndPoBoxAndCityAndCountry(int num, String street, int poBox, String city, String country);
}
