package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.web;

import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.*;
import fr.exalt.businessmicroserviceaccount.domain.ports.input.InputAccountService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.AccountDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/api-account")
@AllArgsConstructor
public class AccountController {
    //input adapter
    private final InputAccountService inputAccountService;

    @PostMapping(value = "/accounts")
    public BankAccount createAccount(@RequestBody AccountDto accountDto) throws RemoteCustomerStateInvalidException,
            AccountTypeInvalidException, RemoteCustomerApiUnreachableException, AccountFieldsInvalidException {
        return inputAccountService.createAccount(accountDto);
    }

    @GetMapping(value = "/accounts")
    public Collection<BankAccount> getAllAccounts() {
        return inputAccountService.getAllAccounts();
    }

    @GetMapping(value = "/accounts/{accountId}")
    public BankAccount getAccount(@PathVariable(name = "accountId") String accountId) throws AccountNotFoundException {
        return inputAccountService.getAccount(accountId);
    }

    @GetMapping(value = "/customers/{customerId}/accounts")
    public Collection<BankAccount> getAccountOfGivenCustomer(@PathVariable(name = "customerId") String customerId) {
        return inputAccountService.getAccountOfGivenCustomer(customerId);
    }

    @PutMapping(value = "/accounts/{accountId}")
    public BankAccount updateAccount(@PathVariable(name = "accountId") String accountId, @RequestBody AccountDto dto)
            throws AccountTypeInvalidException, AccountFieldsInvalidException, AccountNotFoundException,
            RemoteCustomerStateInvalidException, RemoteCustomerApiUnreachableException {
        return inputAccountService.updateAccount(accountId, dto);
    }
}
