package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models;

import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customers")
public class CustomerModel {
    @Id
    @GenericGenerator(name = "uuid")
    private String customerId;
    private String firstname;
    private String lastname;
    private String state;
    private String createdAt;
    private String addressId;
    @Transient
    private Address address;
}
