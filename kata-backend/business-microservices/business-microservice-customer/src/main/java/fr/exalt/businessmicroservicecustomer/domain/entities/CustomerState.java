package fr.exalt.businessmicroservicecustomer.domain.entities;

public enum CustomerState {
    ACTIVE("active"),
    ARCHIVE("archive");
    private final String state;

    CustomerState(String state) {
        this.state = state;
    }
    public String getState() {
        return state;
    }
}
