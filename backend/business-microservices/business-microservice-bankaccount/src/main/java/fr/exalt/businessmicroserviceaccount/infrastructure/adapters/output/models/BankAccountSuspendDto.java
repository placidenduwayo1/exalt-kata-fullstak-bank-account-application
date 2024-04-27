package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountSuspendDto {
    private String accountId;
    private String state;
}
