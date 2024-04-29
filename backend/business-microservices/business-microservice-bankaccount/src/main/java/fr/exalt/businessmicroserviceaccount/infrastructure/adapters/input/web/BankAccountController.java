package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.web;

import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.CurrentBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.SavingBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.*;
import fr.exalt.businessmicroserviceaccount.domain.ports.input.InputBankAccountService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountInterestRateDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountOverdraftDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountSuspendDto;
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
            RemoteCustomerStateInvalidException, RemoteCustomerApiUnreachableException, BankAccountStateInvalidException{
        return inputBankAccountService.updateAccount(accountId, dto);
    }
    @PostMapping(value = "/accounts/switch-state")
    public BankAccount switchAccountState(@RequestBody BankAccountSuspendDto dto) throws BankAccountNotFoundException,
            BankAccountStateInvalidException, BankAccountSameStateException, RemoteCustomerApiUnreachableException,
            RemoteCustomerStateInvalidException {
        return inputBankAccountService.switchAccountState(dto);
    }
    @PostMapping(value = "/accounts/overdraft")
    public CurrentBankAccount changeOverdraft(@RequestBody BankAccountOverdraftDto dto) throws BankAccountNotFoundException,
            BankAccountOverdraftInvalidException, BankAccountTypeNotAcceptedException, RemoteCustomerStateInvalidException,
            BankAccountSuspendException, RemoteCustomerApiUnreachableException {
        return inputBankAccountService.changeOverdraft(dto);
    }
    @PostMapping(value = "/accounts/interest-rate")
    public SavingBankAccount changeInterestRate(@RequestBody BankAccountInterestRateDto dto) throws BankAccountNotFoundException,
            BankAccountSuspendException, RemoteCustomerStateInvalidException, RemoteCustomerApiUnreachableException,
            BankAccountTypeNotAcceptedException {
        return inputBankAccountService.changeInterestRate(dto);
    }

}
