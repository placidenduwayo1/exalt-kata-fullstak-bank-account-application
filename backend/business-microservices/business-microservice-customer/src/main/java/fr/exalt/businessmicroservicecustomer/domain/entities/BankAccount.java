package fr.exalt.businessmicroservicecustomer.domain.entities;

public class BankAccount {
    private String accountId;
    private double balance;
    private double overdraft;
    private String createdAt;
    private BankAccount(AccountBuilder builder){
        this.accountId= builder.accountId;
        this.balance = builder.balance;
        this.overdraft = builder.overdraft;
        this.createdAt = builder.createdAt;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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

    public void setOverdraft(double overdraft) {
        this.overdraft = overdraft;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public static class AccountBuilder {
        private String accountId;
        private double balance;
        private double overdraft;
        private String createdAt;

        public AccountBuilder accountId(String accountId) {
            this.accountId = accountId;
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

        public AccountBuilder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public BankAccount build(){
            return new BankAccount(this);
        }
    }

    @Override
    public String toString() {
        return "account [" +
                "account id='" + accountId + '\'' +
                ", balance=" + balance +
                ", overdraft=" + overdraft +
                ", created at='" + createdAt + '\'' +
                ']';
    }
}
