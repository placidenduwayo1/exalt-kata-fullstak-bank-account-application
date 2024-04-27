package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.web;

import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.*;
import fr.exalt.businessmicroserviceaccount.domain.ports.input.InputBankAccountService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.BankAccountDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.BankAccountSuspendDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/api-bank-account")
@AllArgsConstructor
public class BankAccountController {
    //input adapter
    private final InputBankAccountService inputBankAccountService;

    @PostMapping(value = "/accounts")
    public BankAccount createAccount(@RequestBody BankAccountDto bankAccountDto) throws RemoteCustomerStateInvalidException,
            BankAccountTypeInvalidException, RemoteCustomerApiUnreachableException, BankAccountFieldsInvalidException, BankAccountStateInvalidException {
        return inputBankAccountService.createAccount(bankAccountDto);
    }

    @GetMapping(value = "/accounts")
    public Collection<BankAccount> getAllAccounts() {
        return inputBankAccountService.getAllAccounts();
    }

    @GetMapping(value = "/accounts/{accountId}")
    public BankAccount getAccount(@PathVariable(name = "accountId") String accountId) throws BankAccountNotFoundException {
        return inputBankAccountService.getAccount(accountId);
    }

    @GetMapping(value = "/customers/{customerId}/accounts")
    public Collection<BankAccount> getAccountOfGivenCustomer(@PathVariable(name = "customerId") String customerId) {
        return inputBankAccountService.getAccountOfGivenCustomer(customerId);
    }

    @PutMapping(value = "/accounts/{accountId}")
    public BankAccount updateAccount(@PathVariable(name = "accountId") String accountId, @RequestBody BankAccountDto dto)
            throws BankAccountTypeInvalidException, BankAccountFieldsInvalidException, BankAccountNotFoundException,
            RemoteCustomerStateInvalidException, RemoteCustomerApiUnreachableException, BankAccountStateInvalidException,
            BankAccountTypeProvidedDifferentWithAccountTypeRegisteredException {
        return inputBankAccountService.updateAccount(accountId, dto);
    }
    @PostMapping(value = "/accounts/suspend")
    public BankAccount suspendAccount(@RequestBody BankAccountSuspendDto dto) throws BankAccountNotFoundException,
            BankAccountStateInvalidException, BankAccountAlreadySuspendException {
        return inputBankAccountService.suspendAccount(dto);
    }
}
