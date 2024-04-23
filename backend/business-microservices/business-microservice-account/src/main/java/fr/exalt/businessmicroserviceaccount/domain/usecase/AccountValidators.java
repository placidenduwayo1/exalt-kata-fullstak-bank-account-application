package fr.exalt.businessmicroserviceaccount.domain.usecase;

import fr.exalt.businessmicroserviceaccount.domain.entities.AccountType;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.ExceptionMsg;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.AccountDto;

public class AccountValidators {
    private AccountValidators() {
    }

    public static void formatter(AccountDto dto) {
        dto.setType(dto.getType().strip());
        dto.setCustomerId(dto.getCustomerId().strip());
    }

    public static boolean invalidFields(AccountDto dto) {
        return dto.getType().isBlank()
                || dto.getBalance() < 200
                || dto.getOverdraft() < 200
                || dto.getCustomerId().isBlank();
    }

    public static boolean invalidOverdraft(double overdraft) {
        return overdraft < 200;
    }

    public static boolean validAccountType(String type) {
        boolean typeValid = false;
        for (AccountType accType : AccountType.values()) {
            if (accType.getType().equals(type)) {
                typeValid = true;
                break;
            }
        }
        return typeValid;
    }

    public static boolean remoteCustomerUnreachable(String customerId) {
        return customerId.strip().equals(ExceptionMsg.CUSTOMER_API_UNREACHABLE);
    }
}
