package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "addresses")
@Setter
@Getter
@ToString
public class AddressModel {
    @Id
    @GenericGenerator(name = "uuid")
    private String addressId;
    private int streetNum;
    private String streetName;
    private int poBox;
    private String city;
    private String country;
}
