package fr.exalt.businessmicroserviceaccount.domain.entities;

public class CurrentBankAccount extends BankAccount{
    private double overdraft;
    private CurrentBankAccount(AccountBuilder builder){
        super();
        this.overdraft = builder.overdraft;
    }

    public double getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(double overdraft) {
        this.overdraft = overdraft;
    }

    public static class AccountBuilder {
        private double overdraft;

        public AccountBuilder overdraft(double overdraft) {
            this.overdraft = overdraft;
            return this;
        }
        public CurrentBankAccount build(){
            return new CurrentBankAccount(this);
        }
    }
}
