package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.entities;

import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.feignclient.models.CustomerModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "bank_accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", length = 7)
public abstract class BankAccountModel {
    @Id
    @GenericGenerator(name = "uuid")
    private String accountId;
    private String state;
    private double balance;
    private String createdAt;
    @Column(name = "customer_id")
    private String customerId;
    @Transient
    private CustomerModel customerModel;
}
