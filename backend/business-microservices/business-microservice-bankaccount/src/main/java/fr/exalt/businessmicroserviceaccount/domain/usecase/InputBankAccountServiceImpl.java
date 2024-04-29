package fr.exalt.businessmicroserviceaccount.domain.usecase;

import fr.exalt.businessmicroserviceaccount.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.CurrentBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.entities.Customer;
import fr.exalt.businessmicroserviceaccount.domain.entities.SavingBankAccount;
import fr.exalt.businessmicroserviceaccount.domain.exceptions.*;
import fr.exalt.businessmicroserviceaccount.domain.ports.input.InputBankAccountService;
import fr.exalt.businessmicroserviceaccount.domain.ports.output.OutputAccountService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountOverdraftDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountSuspendDto;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
public class InputBankAccountServiceImpl implements InputBankAccountService {
    //output adapter
    private final OutputAccountService outputAccountService;
    private static final String CURRENT = "current";
    private static final String SAVING = "saving";
    private static final String ACTIVE = "active";
    private static final String SUSPEND = "suspended";
    private static final String FORMATTER = "%s,%n%s";

    @Override
    public BankAccount createAccount(BankAccountDto dto) throws BankAccountTypeInvalidException, BankAccountFieldsInvalidException,
            RemoteCustomerApiUnreachableException, RemoteCustomerStateInvalidException {

        Customer customer = outputAccountService.loadRemoteCustomer(dto.getCustomerId());
        validateAccount(dto, customer);

        if (dto.getType().equals(CURRENT)) {
            CurrentBankAccount currentBankAccount = MapperService.mapToCurrentAccount(dto);
            currentBankAccount.setAccountId(UUID.randomUUID().toString());
            currentBankAccount.setCreatedAt(Timestamp.from(Instant.now()).toString());
            currentBankAccount.setState(ACTIVE);
            currentBankAccount.setOverdraft(100);
            // call to output adapter to register current account in db
            CurrentBankAccount savedBankAccount = outputAccountService.createCurrentAccount(currentBankAccount);
            savedBankAccount.setType(CURRENT);
            savedBankAccount.setCustomer(customer);
            return savedBankAccount;
        } else if (dto.getType().equals(SAVING)) {
            SavingBankAccount savingAccount = MapperService.mapToSavingAccount(dto);
            savingAccount.setAccountId(UUID.randomUUID().toString());
            savingAccount.setCreatedAt(Timestamp.from(Instant.now()).toString());
            savingAccount.setState(ACTIVE);
            savingAccount.setInterestRate(3.5);
            // call to output adapter to register current account in db
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
    public BankAccount getAccount(String accountId) throws BankAccountNotFoundException {
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
    public BankAccount updateAccount(String accountId, BankAccountDto dto) throws BankAccountTypeInvalidException,
            BankAccountFieldsInvalidException, BankAccountNotFoundException, RemoteCustomerStateInvalidException,
            RemoteCustomerApiUnreachableException {

        Customer customer = outputAccountService.loadRemoteCustomer(dto.getCustomerId());
        validateAccount(dto, customer);
        BankAccount bankAccount = getAccount(accountId);
        if (dto.getType().equals(SAVING)) {
            SavingBankAccount savingAccount = MapperService.mapToSavingAccount(dto);
            savingAccount.setBalance(savingAccount.getBalance() + bankAccount.getBalance());

            // call to output adapter to save updated account in db
            SavingBankAccount updateSavingAccount = outputAccountService.updateSavingAccount(savingAccount);
            updateSavingAccount.setCustomer(customer);

            return updateSavingAccount;

        } else if (dto.getType().equals(CURRENT)) {
            CurrentBankAccount currentAccount = MapperService.mapToCurrentAccount(dto);
            currentAccount.setBalance(currentAccount.getBalance() + bankAccount.getBalance());

            // call to output adapter to save updated account in db
            CurrentBankAccount updateCurrentAccount = outputAccountService.updateCurrentAccount(currentAccount);
            updateCurrentAccount.setCustomer(customer);

            return updateCurrentAccount;
        }
        return null;
    }

    @Override
    public BankAccount suspendAccount(BankAccountSuspendDto dto) throws BankAccountNotFoundException,
            BankAccountStateInvalidException, BankAccountAlreadySuspendException, RemoteCustomerApiUnreachableException {
        BankAccount savedBankAccount = getAccount(dto.getAccountId());
        if (!BankAccountValidators.validAccountState(dto.getState()))
            throw new BankAccountStateInvalidException(ExceptionMsg.ACCOUNT_STATE_INVALID);

        if (savedBankAccount.getState().equals(SUSPEND)) {
            throw new BankAccountAlreadySuspendException(ExceptionMsg.BANK_ACCOUNT_SUSPEND);
        }
        savedBankAccount.setState(SUSPEND);
        // call to output adapter to suspend account in db updated account
        BankAccount suspendAccount = outputAccountService.suspendAccount(savedBankAccount);
        Customer customer = outputAccountService.loadRemoteCustomer(suspendAccount.getCustomerId());
        if(BankAccountValidators.remoteCustomerApiUnreachable(customer.getCustomerId())){
            throw new RemoteCustomerApiUnreachableException(ExceptionMsg.REMOTE_CUSTOMER_API);
        }
        if (suspendAccount instanceof CurrentBankAccount current) {
            current.setType(CURRENT);
            current.setCustomer(customer);
            return current;
        } else if (suspendAccount instanceof SavingBankAccount saving) {
            saving.setType(SAVING);
            saving.setCustomer(customer);
            return saving;
        }
        return null;
    }

    @Override
    public CurrentBankAccount updateOverdraft(BankAccountOverdraftDto dto) throws BankAccountNotFoundException,
            BankAccountGivenSavingException, BankAccountAlreadySuspendException, BankAccountOverdraftInvalidException,
            RemoteCustomerStateInvalidException {

        BankAccount bankAccount = getAccount(dto.getAccountId());
        Customer customer = outputAccountService.loadRemoteCustomer(bankAccount.getCustomerId());
        if (customer.getState().equals("archive")) {
            throw new RemoteCustomerStateInvalidException(ExceptionMsg.REMOTE_CUSTOMER_STATE);
        }
        if (bankAccount.getState().equals(SUSPEND)) {
            throw new BankAccountAlreadySuspendException(ExceptionMsg.BANK_ACCOUNT_SUSPEND);
        } else if (bankAccount instanceof SavingBankAccount saving) {
            throw new BankAccountGivenSavingException(
                    String.format(FORMATTER, ExceptionMsg.BANK_ACCOUNT_SAVING, saving));
        } else if (bankAccount instanceof CurrentBankAccount && BankAccountValidators.invalidOverdraft(dto.getOverdraft())) {
            throw new BankAccountOverdraftInvalidException(ExceptionMsg.BANK_ACCOUNT_OVERDRAFT);
        } else if (bankAccount instanceof CurrentBankAccount current) {
            current.setOverdraft(dto.getOverdraft());
            CurrentBankAccount savedCurrent = outputAccountService.updateOverdraft(current);
            savedCurrent.setType(CURRENT);
            savedCurrent.setCustomer(customer);
            return savedCurrent;
        } else
            return null;
    }

    private void validateAccount(BankAccountDto dto, Customer customer) throws BankAccountTypeInvalidException,
            BankAccountFieldsInvalidException, RemoteCustomerApiUnreachableException, RemoteCustomerStateInvalidException {
        BankAccountValidators.formatter(dto);
        if (!BankAccountValidators.validAccountSType(dto.getType()))
            throw new BankAccountTypeInvalidException(ExceptionMsg.ACCOUNT_TYPE_INVALID);

        if (BankAccountValidators.invalidFields(dto))
            throw new BankAccountFieldsInvalidException(ExceptionMsg.ACCOUNT_FIELDS_INVALID);

        if (BankAccountValidators.remoteCustomerApiUnreachable(customer.getCustomerId()))
            throw new RemoteCustomerApiUnreachableException(String.format(FORMATTER, ExceptionMsg.REMOTE_CUSTOMER_API, customer));
        else if (BankAccountValidators.remoteCustomerStateInvalid(customer.getState()))
            throw new RemoteCustomerStateInvalidException(String.format(FORMATTER, ExceptionMsg.REMOTE_CUSTOMER_STATE, customer));

    }

    private Collection<BankAccount> setCustomerToAccount(Collection<BankAccount> bankAccounts) {
        return bankAccounts.stream()
                .map(account -> {
                    Customer remoteCustomer = outputAccountService.loadRemoteCustomer(account.getCustomerId());
                    account.setCustomer(remoteCustomer);
                    if (account instanceof CurrentBankAccount current) {
                        current.setType(CURRENT);
                    } else if (account instanceof SavingBankAccount saving) {
                        saving.setType(SAVING);
                    }
                    return account;
                })
                .toList();
    }
}
