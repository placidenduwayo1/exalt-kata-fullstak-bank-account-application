package fr.exalt.businessmicroserviceoperation.domain.entities;

public enum OperationType {
    DEPOSIT("depot"),
    WITHDRAW("retrait");
    private final String type;

    OperationType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
