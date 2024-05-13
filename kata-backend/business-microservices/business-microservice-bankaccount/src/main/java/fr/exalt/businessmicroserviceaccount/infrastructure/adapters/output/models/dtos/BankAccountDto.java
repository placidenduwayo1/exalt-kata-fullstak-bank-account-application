package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class BankAccountDto {
    private String type;
    private double balance;
    private String customerId;
}
