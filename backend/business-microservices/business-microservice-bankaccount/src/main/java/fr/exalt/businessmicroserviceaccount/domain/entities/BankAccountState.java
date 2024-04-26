package fr.exalt.businessmicroserviceaccount.domain.entities;

public enum BankAccountState {
    ACTIVE("active"),
    SUSPENDED("suspendu");
    private final String state;
    BankAccountState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
