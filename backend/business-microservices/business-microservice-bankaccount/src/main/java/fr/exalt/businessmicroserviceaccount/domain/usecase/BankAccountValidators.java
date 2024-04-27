package fr.exalt.businessmicroserviceaccount.domain.usecase;

import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccountState;
import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccountType;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.BankAccountDto;

public class BankAccountValidators {
    private BankAccountValidators() {
    }

    public static void formatter(BankAccountDto dto) {
        dto.setType(dto.getType().strip());
        dto.setCustomerId(dto.getCustomerId().strip());
    }

    public static boolean invalidFields(BankAccountDto dto) {
        return dto.getType().isBlank()
                || dto.getCustomerId().isBlank();
    }
    public static boolean validAccountSType(String type) {
        for (BankAccountType accType : BankAccountType.values()) {
            if (accType.getAccountType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    public static boolean validAccountState(String state) {
        for (BankAccountState accState : BankAccountState.values()) {
            if (accState.getState().equals(state)) {
                return true;
            }
        }
        return false;
    }

    public static boolean remoteCustomerApiUnreachable(String customerId) {
        return customerId.strip().equals("it may be possible that remote customer is unreachable");
    }
    public static boolean remoteCustomerStateInvalid(String customerState){
        return customerState.equals("archive");
    }
}
