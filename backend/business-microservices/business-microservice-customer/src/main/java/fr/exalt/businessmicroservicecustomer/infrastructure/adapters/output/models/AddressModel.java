package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "addresses")
public class AddressModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String addressId;
    private int streetNum;
    private String streetName;
    private int poBox;
    private String city;
    private String country;
}
