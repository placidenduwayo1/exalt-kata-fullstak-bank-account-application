package fr.exalt.businessmicroservicecustomer.domain.entities;

public enum State {
    ACTIVE("active"),
    ARCHIVE("archive");
    private final String customerState;

    State(String customerState) {
        this.customerState = customerState;
    }
    public String getCustomerState() {
        return customerState;
    }
}
