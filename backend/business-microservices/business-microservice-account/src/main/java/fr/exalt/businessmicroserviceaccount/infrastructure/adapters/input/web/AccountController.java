package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.web;

import fr.exalt.businessmicroserviceaccount.domain.entities.Account;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.AccountFieldsInvalidException;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.AccountTypeInvalidException;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.RemoteCustomerApiUnreachableException;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.RemoteCustomerStateInvalidException;
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
    public Account createAccount(@RequestBody AccountDto accountDto) throws RemoteCustomerStateInvalidException,
            AccountTypeInvalidException, RemoteCustomerApiUnreachableException, AccountFieldsInvalidException {
        return inputAccountService.createAccount(accountDto);
    }
    @GetMapping(value = "/accounts")
    public Collection<Account> getAllAccounts(){
        return inputAccountService.getAllAccounts();
    }
}
