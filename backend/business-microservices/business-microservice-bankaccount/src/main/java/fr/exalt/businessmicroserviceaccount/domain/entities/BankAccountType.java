package fr.exalt.businessmicroserviceaccount.domain.entities;

public enum BankAccountType {
    CURRENT("current"),
    SAVING("saving");
    private final String accountType;

    BankAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountType() {
        return accountType;
    }
}
