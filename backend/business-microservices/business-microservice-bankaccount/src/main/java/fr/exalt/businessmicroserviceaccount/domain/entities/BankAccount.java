package fr.exalt.businessmicroserviceaccount.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BankAccount {
    private String accountId;
    private String type;
    private String state;
    private double balance;
    private String createdAt;
    private String customerId;
    private Customer customer;

    @Override
    public String toString() {
        return "Account [" +
                "account id='" + accountId + '\'' +
                ", type "+type+
                ", state='" + state + '\'' +
                ", balance=" + balance +
                ", created at" + createdAt +
                ", customer id='" + customerId + '\'' +
                ", customer=" + customer +
                ']';
    }
}
