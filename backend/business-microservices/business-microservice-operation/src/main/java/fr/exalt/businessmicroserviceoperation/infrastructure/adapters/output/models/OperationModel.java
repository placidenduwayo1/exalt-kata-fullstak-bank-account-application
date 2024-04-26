package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models;

import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.BankAccountModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "operations")
public class OperationModel {
    @Id
    @GenericGenerator(name = "uuid")
    private String operationId;
    private String type;
    private double mount;
    private String createdAt;
    @Column(name = "account_id")
    private String accountId;
    @Transient
    private BankAccountModel account;
}
