package fr.exalt.businessmicroserviceaccount.domain.entities;

public enum BankAccountType {
    CURRENT("compte-courant"),
    SAVING("compte-epargne");
    private final String accountType;

    BankAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountType() {
        return accountType;
    }
}
