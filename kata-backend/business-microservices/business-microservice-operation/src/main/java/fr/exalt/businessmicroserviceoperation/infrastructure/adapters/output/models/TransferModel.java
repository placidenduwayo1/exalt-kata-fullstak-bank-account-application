package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models;

import fr.exalt.businessmicroserviceoperation.domain.entities.BankAccount;
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
@Table(name = "transfers")
public class TransferModel {
    @Id
    @GenericGenerator(name = "uuid")
    private String transferId;
    private String origin;
    @Transient
    private BankAccountModel accountOrigin;
    private String destination;
    @Transient
    private BankAccountModel accountDestination;
    private double mount;
    private String createdAt;
}
