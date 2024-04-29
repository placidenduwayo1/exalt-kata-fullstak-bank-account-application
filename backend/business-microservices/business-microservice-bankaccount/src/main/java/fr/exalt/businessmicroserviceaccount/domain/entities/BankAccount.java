package fr.exalt.businessmicroserviceaccount.domain.entities;

public abstract class BankAccount {
    private String accountId;
    private String type;
    private String state;
    private double balance;
    private String createdAt;
    private String customerId;
    private Customer customer;

    BankAccount() {
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Account [" +
                "account id='" + accountId + '\'' +
                ", type=" + type +
                ", state='" + state + '\'' +
                ", balance=" + balance +
                ", created at" + createdAt +
                ", customer id='" + customerId + '\'' +
                ", customer=" + customer +
                ']';
    }
}
