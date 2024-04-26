package fr.exalt.businessmicroserviceaccount.domain.entities;

public enum AccountType {
    CURRENT ("compte-courant"),
    SAVING("compte-epargne");
    private final String type;
    AccountType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
