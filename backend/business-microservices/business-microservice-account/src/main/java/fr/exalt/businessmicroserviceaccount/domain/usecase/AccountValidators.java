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

    public static boolean remoteCustomerApiUnreachable(String customerId) {
        return customerId.strip().equals("it is possible that remote customer is unreachable");
    }
    public static boolean remoteCustomerStateInvalid(String customerState){
        return customerState.equals("archive");
    }
}
