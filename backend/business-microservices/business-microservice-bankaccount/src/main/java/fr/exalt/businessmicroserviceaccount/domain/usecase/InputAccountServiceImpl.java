package fr.exalt.businessmicroserviceaccount.domain.usecase;

import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.Customer;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.*;
import fr.exalt.businessmicroserviceaccount.domain.ports.input.InputAccountService;
import fr.exalt.businessmicroserviceaccount.domain.ports.output.OutputAccountService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.BankAccountDto;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
public class InputAccountServiceImpl implements InputAccountService {
    //output adapter
    private final OutputAccountService outputAccountService;

    @Override
    public BankAccount createAccount(BankAccountDto bankAccountDto) throws AccountTypeInvalidException, AccountFieldsInvalidException,
            RemoteCustomerApiUnreachableException, RemoteCustomerStateInvalidException {

        Customer customer = outputAccountService.loadRemoteCustomer(bankAccountDto.getCustomerId().strip());
        validateAccount(bankAccountDto, customer);
        BankAccount bankAccount = MapperService.fromTo(bankAccountDto);
        bankAccount.setAccountId(UUID.randomUUID().toString());
        bankAccount.setCreatedAt(Timestamp.from(Instant.now()).toString());
        if (bankAccount.getType().equals("compte-epargne")) {
            bankAccount.setOverdraft(0);//un compte epargne ne possede pas de decouvert
        }
        // passer le bean créé à l'adaptateur pour l'enregistrer en db
        BankAccount saveBankAccount = outputAccountService.createAccount(bankAccount);
        saveBankAccount.setCustomer(customer);
        return saveBankAccount;
    }

    @Override
    public Collection<BankAccount> getAllAccounts() {
        Collection<BankAccount> bankAccounts = outputAccountService.getAllAccounts();
        return setCustomerToAccount(bankAccounts);
    }

    @Override
    public BankAccount getAccount(String accountId) throws AccountNotFoundException {
        BankAccount bankAccount = outputAccountService.getAccount(accountId);
        Customer customer = outputAccountService.loadRemoteCustomer(bankAccount.getCustomerId());
        bankAccount.setCustomer(customer);
        return bankAccount;
    }

    @Override
    public Collection<BankAccount> getAccountOfGivenCustomer(String customerId) {
        Collection<BankAccount> bankAccounts = outputAccountService.getAccountOfGivenCustomer(customerId);
        return setCustomerToAccount(bankAccounts);
    }

    @Override
    public BankAccount updateAccount(String accountId, BankAccountDto bankAccountDto) throws AccountTypeInvalidException,
            AccountFieldsInvalidException, AccountNotFoundException, RemoteCustomerStateInvalidException,
            RemoteCustomerApiUnreachableException {
        Customer customer = outputAccountService.loadRemoteCustomer(bankAccountDto.getCustomerId().strip());
        validateAccount(bankAccountDto, customer);
        BankAccount bankAccount = getAccount(accountId);
        bankAccount.setBalance(bankAccount.getBalance() + bankAccountDto.getBalance());
        bankAccount.setCustomerId(bankAccountDto.getCustomerId());
        BankAccount updatedBankAccount = outputAccountService.updateAccount(bankAccount);
        updatedBankAccount.setCustomer(customer);
        return updatedBankAccount;
    }

    private void validateAccount(BankAccountDto dto, Customer customer) throws AccountTypeInvalidException,
            AccountFieldsInvalidException, RemoteCustomerApiUnreachableException, RemoteCustomerStateInvalidException {
        AccountValidators.formatter(dto);
        if (!AccountValidators.validAccountType(dto.getType())) {
            throw new AccountTypeInvalidException(ExceptionMsg.ACCOUNT_TYPE_INVALID);
        }
        if (AccountValidators.invalidFields(dto)) {
            throw new AccountFieldsInvalidException(ExceptionMsg.ACCOUNT_FIELDS_INVALID);
        }
        if (AccountValidators.remoteCustomerApiUnreachable(customer.getCustomerId())) {
            throw new RemoteCustomerApiUnreachableException(String.format("%s,%n%s", ExceptionMsg.REMOTE_CUSTOMER_API, customer));
        } else if (AccountValidators.remoteCustomerStateInvalid(customer.getState())) {
            throw new RemoteCustomerStateInvalidException(String.format("%s,%n%s", ExceptionMsg.REMOTE_CUSTOMER_STATE, customer));
        }
    }

    private Collection<BankAccount> setCustomerToAccount(Collection<BankAccount> bankAccounts) {
        return bankAccounts.stream()
                .map(account -> {
                    Customer remoteCustomer = outputAccountService.loadRemoteCustomer(account.getCustomerId());
                    account.setCustomer(remoteCustomer);
                    return account;
                })
                .toList();
    }
}
