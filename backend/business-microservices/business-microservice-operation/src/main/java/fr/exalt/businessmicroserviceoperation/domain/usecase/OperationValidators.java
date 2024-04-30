package fr.exalt.businessmicroserviceoperation.domain.usecase;

import fr.exalt.businessmicroserviceoperation.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceoperation.domain.entities.OperationType;
import fr.exalt.businessmicroserviceoperation.domain.exceptions.ExceptionsMsg;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.OperationDto;

public class OperationValidators {
    private OperationValidators() {
    }

    public static void formatter(OperationDto dto) {
        dto.setAccountId(dto.getAccountId().strip());
        dto.setType(dto.getType().strip());
    }

    public static boolean invalidOperationRequest(OperationDto dto) {
        return dto.getType().isBlank()
                || dto.getMount() < 10;
    }

    public static boolean invalidOperationType(String type) {
        for (OperationType opType : OperationType.values()) {
            if (type.equals(opType.getType())) {
                return true;
            }
        }
        return false;
    }

    public static boolean enoughBalance(BankAccount bankAccount, double operationAmount) {
        return bankAccount.getBalance() + bankAccount.getOverdraft() >= operationAmount;
    }

    public static boolean remoteAccountApiUnreachable(String accountId) {
        return accountId.strip().equals(ExceptionsMsg.REMOTE_CUSTOMER_UNREACHABLE);
    }
}
