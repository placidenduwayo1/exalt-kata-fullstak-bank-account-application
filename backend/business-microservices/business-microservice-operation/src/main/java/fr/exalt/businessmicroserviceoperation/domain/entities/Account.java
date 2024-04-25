package fr.exalt.businessmicroserviceoperation.domain.entities;

public class Account {
    private String accountId;
    private String type;
    private double balance;
    private double overdraft;
    private Account(AccountBuilder builder) {
        this.accountId = builder.accountId;
        this.type = builder.type;
        this.balance = builder.balance;
        this.overdraft = builder.overdraft;
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

    public void setOverdraft(double overdraft) {
        this.overdraft = overdraft;
    }

    public static class AccountBuilder {
        private String accountId;
        private String type;
        private double balance;
        private double overdraft;

        public AccountBuilder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public AccountBuilder type(String type) {
            this.type = type;
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


        public Account build() {
            return new Account(this);
        }
    }

    @Override
    public String toString() {
        return "Account [" +
                "account id='" + accountId + '\'' +
                ", type='" + type + '\'' +
                ", balance=" + balance +
                ", overdraft=" + overdraft +
                ']';
    }
}
