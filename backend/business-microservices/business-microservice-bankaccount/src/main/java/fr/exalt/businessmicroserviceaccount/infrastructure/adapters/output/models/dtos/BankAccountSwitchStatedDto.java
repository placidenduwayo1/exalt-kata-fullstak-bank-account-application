package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class BankAccountSwitchStatedDto {
    private String accountId;
    private String state;
}
