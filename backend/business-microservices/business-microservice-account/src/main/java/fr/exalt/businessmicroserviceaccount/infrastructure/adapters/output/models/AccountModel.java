package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models;

import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.feignclient.models.CustomerModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "accounts")
public class AccountModel {
    @Id
    @GenericGenerator(name = "uuid")
    private String accountId;
    private String type;
    private double balance;
    private double overdraft;
    private String createdAt;
    @Column(name = "customer_id")
    private String customerId;
    @Transient
    private CustomerModel customerModel;
}
