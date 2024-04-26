package fr.exalt.businessmicroserviceaccount.domain.usecase;

import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.CurrentBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.Customer;
import fr.exalt.businessmicroserviceaccount.domain.entities.SavingBankAccount;
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
    private static final String CURRENT = "compte-courant";
    private static final String SAVING = "compte-epargne";

    @Override
    public BankAccount createAccount(BankAccountDto dto) throws AccountTypeInvalidException, AccountFieldsInvalidException,
            RemoteCustomerApiUnreachableException, RemoteCustomerStateInvalidException, AccountStateInvalidException {

        Customer customer = outputAccountService.loadRemoteCustomer(dto.getCustomerId().strip());
        validateAccount(dto, customer);

        if (dto.getType().equals(CURRENT)) {
            CurrentBankAccount currentBankAccount = MapperService.mapToCurrentAccount(dto);
            currentBankAccount.setAccountId(UUID.randomUUID().toString());
            currentBankAccount.setCreatedAt(Timestamp.from(Instant.now()).toString());
            // call to output adapter to register current account
            CurrentBankAccount savedBankAccount = outputAccountService.createCurrentAccount(currentBankAccount);
            savedBankAccount.setType(CURRENT);
            savedBankAccount.setCustomer(customer);
            return savedBankAccount;
        } else if (dto.getType().equals(SAVING)) {
            SavingBankAccount savingAccount = MapperService.mapToSavingAccount(dto);
            savingAccount.setAccountId(UUID.randomUUID().toString());
            savingAccount.setCreatedAt(Timestamp.from(Instant.now()).toString());
            // call to output adapter to register current account
            SavingBankAccount savedAccount = outputAccountService.createSavingAccount(savingAccount);
            savedAccount.setType(SAVING);
            savedAccount.setCustomer(customer);
            return savedAccount;
        }

        return null;
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
            RemoteCustomerApiUnreachableException, AccountStateInvalidException, AccountTypeProvidedDifferentWithAccountTypeRegisteredException {

        Customer customer = outputAccountService.loadRemoteCustomer(bankAccountDto.getCustomerId().strip());
        validateAccount(bankAccountDto, customer);

        BankAccount bankAccount = getAccount(accountId);
        if (!bankAccount.getType().equals(bankAccountDto.getType()))
            throw new AccountTypeProvidedDifferentWithAccountTypeRegisteredException(ExceptionMsg.ACCOUNTS_TYPE_DIFFERENT);
        if (bankAccountDto.getType().equals(SAVING)) {
            SavingBankAccount savingAccount = MapperService.mapToSavingAccount(bankAccountDto);
            savingAccount.setBalance(savingAccount.getBalance() + bankAccount.getBalance());

            // call to output adapter to save updated account
            SavingBankAccount updateSavingAccount = outputAccountService.updateSavingAccount(savingAccount);
            updateSavingAccount.setCustomer(customer);

            return updateSavingAccount;

        } else if (bankAccountDto.getType().equals(CURRENT)) {
            CurrentBankAccount currentAccount = MapperService.mapToCurrentAccount(bankAccountDto);
            currentAccount.setBalance(currentAccount.getBalance() + bankAccount.getBalance());

            // call to output adapter to save updated account
            CurrentBankAccount updateCurrentAccount = outputAccountService.updateCurrentAccount(currentAccount);
            updateCurrentAccount.setCustomer(customer);

            return updateCurrentAccount;
        }
        return null;
    }

    private void validateAccount(BankAccountDto dto, Customer customer) throws AccountTypeInvalidException,
            AccountFieldsInvalidException, RemoteCustomerApiUnreachableException, RemoteCustomerStateInvalidException,
            AccountStateInvalidException {
        AccountValidators.formatter(dto);
        if (!AccountValidators.validAccountSType(dto.getType()))
            throw new AccountTypeInvalidException(ExceptionMsg.ACCOUNT_TYPE_INVALID);

        if (!AccountValidators.validAccountState(dto.getState()))
            throw new AccountStateInvalidException(ExceptionMsg.ACCOUNT_STATE_INVALID);

        if (AccountValidators.invalidFields(dto))
            throw new AccountFieldsInvalidException(ExceptionMsg.ACCOUNT_FIELDS_INVALID);

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
