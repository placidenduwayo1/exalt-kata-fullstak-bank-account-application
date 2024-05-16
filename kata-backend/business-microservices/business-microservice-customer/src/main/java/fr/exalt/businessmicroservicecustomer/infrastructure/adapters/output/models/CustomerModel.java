package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customers")
@Setter
@Getter
@ToString
public class CustomerModel {
    @Id
    @GenericGenerator(name = "uuid")
    private String customerId;
    private String firstname;
    private String lastname;
    private String state;
    private String email;
    private String createdAt;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id" )
    private AddressModel address;
}
