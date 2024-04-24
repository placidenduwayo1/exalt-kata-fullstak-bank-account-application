package fr.exalt.businessmicroservicecustomer.domain.ports.output;

import fr.exalt.businessmicroservicecustomer.domain.entities.Account;

import java.util.Collection;

public interface OutputRemoteAccountService {
    Collection<Account> loadRemoteAccountsOgCustomer(String customerId);
}
