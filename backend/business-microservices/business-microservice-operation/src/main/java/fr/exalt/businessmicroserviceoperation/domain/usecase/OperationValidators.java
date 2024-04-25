package fr.exalt.businessmicroserviceoperation.domain.usecase;

import fr.exalt.businessmicroserviceoperation.domain.entities.Account;
import fr.exalt.businessmicroserviceoperation.domain.entities.OperationType;
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

    public static boolean notEnoughBalance(Account account, double operationAmount) {
        return account.getBalance() + account.getOverdraft() < operationAmount;
    }
}