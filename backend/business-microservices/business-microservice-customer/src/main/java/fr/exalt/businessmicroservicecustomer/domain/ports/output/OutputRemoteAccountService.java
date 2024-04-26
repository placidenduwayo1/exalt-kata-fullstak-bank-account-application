package fr.exalt.businessmicroservicecustomer.domain.ports.output;

import fr.exalt.businessmicroservicecustomer.domain.entities.BankAccount;

import java.util.Collection;

public interface OutputRemoteAccountService {
    Collection<BankAccount> loadRemoteAccountsOgCustomer(String customerId);
}
