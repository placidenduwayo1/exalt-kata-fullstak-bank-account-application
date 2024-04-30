package fr.exalt.businessmicroserviceoperation.domain.entities;

public class BankAccount {
    private String accountId;
    private String type;
    private String state;
    private double balance;
    private double overdraft;
    private String customerId;
    private Customer customer;

    private BankAccount(AccountBuilder builder) {
        this.accountId = builder.accountId;
        this.type = builder.type;
        this.state = builder.state;
        this.balance = builder.balance;
        this.overdraft = builder.overdraft;
        this.customerId = builder.customerId;
        this.customer = builder.customer;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getOverdraft() {
        return overdraft;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setOverdraft(double overdraft) {
        this.overdraft = overdraft;
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

    public static class AccountBuilder {
        private String accountId;
        private String type;
        private String state;
        private double balance;
        private double overdraft;
        private String customerId;
        private Customer customer;

        public AccountBuilder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public AccountBuilder type(String type) {
            this.type = type;
            return this;
        }

        public AccountBuilder state(String state) {
            this.state = state;
            return this;
        }

        public AccountBuilder balance(double balance) {
            this.balance = balance;
            return this;
        }

        public AccountBuilder overdraft(double overdraft) {
            this.overdraft = overdraft;
            return this;
        }

        public AccountBuilder customerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public AccountBuilder customer(Customer customer) {
            this.customer = customer;
            return this;
        }


        public BankAccount build() {
            return new BankAccount(this);
        }
    }

    @Override
    public String toString() {
        return "Bank Account [" +
                "account id='" + accountId + '\'' +
                ", type='" + type + '\'' +
                ", state='" + state + '\'' +
                ", balance=" + balance +
                ", overdraft=" + overdraft +
                ", customer=" + customer +
                '}';
    }
}
