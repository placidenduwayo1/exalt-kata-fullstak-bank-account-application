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
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountInterestRateDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountOverdraftDto;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.dtos.BankAccountSwitchStatedDto;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

public class InputBankAccountServiceImpl implements InputBankAccountService {
    private final OutputAccountService outputAccountService;//output adapter
    private static final String BANK_ACCOUNT_CURRENT = "current";
    private static final String BANK_ACCOUNT_SAVING = "saving";
    private static final String BANK_ACCOUNT_ACTIVE = "active";
    private static final String BANK_ACCOUNT_SUSPEND = "suspended";
    private static final String FORMATTER = "%s,%n%s";
    private static final double INITIAL_OVERDRAFT = 100;
    private static final double INITIAL_INTEREST = 3.5;

    public InputBankAccountServiceImpl(OutputAccountService outputAccountService) {
        this.outputAccountService = outputAccountService;
    }

    @Override
    public BankAccount createAccount(BankAccountDto dto) throws BankAccountTypeInvalidException, BankAccountFieldsInvalidException,
            RemoteCustomerApiUnreachableException, RemoteCustomerStateInvalidException {
        validateAccount(dto);
        Customer customer = outputAccountService.loadRemoteCustomer(dto.getCustomerId());
        validateRemoteCustomer(customer.getCustomerId(), customer.getState());
        if (dto.getType().equals(BANK_ACCOUNT_CURRENT)) {
            CurrentBankAccount currentBankAccount = MapperService.mapToCurrentAccount(dto);
            currentBankAccount.setAccountId(UUID.randomUUID().toString());
            currentBankAccount.setCreatedAt(Timestamp.from(Instant.now()).toString());
            currentBankAccount.setState(BANK_ACCOUNT_ACTIVE);
            currentBankAccount.setOverdraft(INITIAL_OVERDRAFT);
            // call to output adapter to register current account in db
            CurrentBankAccount savedBankAccount = outputAccountService.createCurrentAccount(currentBankAccount);
            savedBankAccount.setType(BANK_ACCOUNT_CURRENT);
            savedBankAccount.setCustomer(customer);
            return savedBankAccount;
        } else if (dto.getType().equals(BANK_ACCOUNT_SAVING)) {
            SavingBankAccount savingAccount = MapperService.mapToSavingAccount(dto);
            savingAccount.setAccountId(UUID.randomUUID().toString());
            savingAccount.setCreatedAt(Timestamp.from(Instant.now()).toString());
            savingAccount.setState(BANK_ACCOUNT_ACTIVE);
            savingAccount.setInterestRate(INITIAL_INTEREST);
            // call to output adapter to register current account in db
            SavingBankAccount savedAccount = outputAccountService.createSavingAccount(savingAccount);
            savedAccount.setType(BANK_ACCOUNT_SAVING);
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
        if (bankAccount instanceof CurrentBankAccount current) {
            current.setType(BANK_ACCOUNT_CURRENT);
        } else if (bankAccount instanceof SavingBankAccount saving) {
            saving.setType(BANK_ACCOUNT_SAVING);
        }
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
        validateRemoteCustomer(customer.getCustomerId(), customer.getState());
        validateAccount(dto);

        BankAccount bankAccount = getAccount(accountId);

        if (dto.getType().equals(BANK_ACCOUNT_SAVING)) {
            SavingBankAccount savingBankAccount = new SavingBankAccount.SavingAccountBuilder().build();
            savingBankAccount.setAccountId(bankAccount.getAccountId());
            savingBankAccount.setState(bankAccount.getState());
            savingBankAccount.setBalance(dto.getBalance() + bankAccount.getBalance());
            savingBankAccount.setCustomerId(dto.getCustomerId());
            savingBankAccount.setCreatedAt(bankAccount.getCreatedAt());
            // call to output adapter to save updated account in db
            SavingBankAccount updateSavingAccount = outputAccountService.updateSavingAccount(savingBankAccount);
            updateSavingAccount.setType(BANK_ACCOUNT_SAVING);
            updateSavingAccount.setCustomer(customer);
            return updateSavingAccount;

        } else if (dto.getType().equals(BANK_ACCOUNT_CURRENT)) {
            CurrentBankAccount currentAccount = new CurrentBankAccount.CurrentAccountBuilder().build();
            currentAccount.setAccountId(bankAccount.getAccountId());
            currentAccount.setState(bankAccount.getState());
            currentAccount.setBalance(dto.getBalance() + bankAccount.getBalance());
            currentAccount.setCustomerId(dto.getCustomerId());
            currentAccount.setCreatedAt(bankAccount.getCreatedAt());
            // call to output adapter to save updated account in db
            CurrentBankAccount updateCurrentAccount = outputAccountService.updateCurrentAccount(currentAccount);
            updateCurrentAccount.setType(BANK_ACCOUNT_CURRENT);
            updateCurrentAccount.setCustomer(customer);
            return updateCurrentAccount;
        }
        return null;
    }

    @Override
    public BankAccount switchAccountState(BankAccountSwitchStatedDto dto) throws BankAccountNotFoundException,
            BankAccountStateInvalidException, BankAccountSameStateException, RemoteCustomerApiUnreachableException,
            RemoteCustomerStateInvalidException {

        BankAccount bankAccount = getAccount(dto.getAccountId());
        Customer customer = outputAccountService.loadRemoteCustomer(bankAccount.getCustomerId());
        validateRemoteCustomer(customer.getCustomerId(), customer.getState());
        if (!BankAccountValidators.validAccountState(dto.getState()))
            throw new BankAccountStateInvalidException(ExceptionMsg.BANK_ACCOUNT_STATE_INVALID);

        else if (bankAccount.getState().equals(dto.getState()))
            throw new BankAccountSameStateException(ExceptionMsg.BANK_ACCOUNT_SAME_STATE);
        else {
            bankAccount.setState(dto.getState());
            // call to output adapter to save updated account in db
            BankAccount switchedAccountState = outputAccountService.switchAccountState(bankAccount);
            switchedAccountState.setType(bankAccount.getType());
            switchedAccountState.setCustomer(customer);
            return switchedAccountState;
        }
    }

    @Override
    public BankAccount changeOverdraft(BankAccountOverdraftDto dto) throws BankAccountNotFoundException,
            RemoteCustomerStateInvalidException, BankAccountSuspendException, BankAccountTypeNotAcceptedException,
            RemoteCustomerApiUnreachableException, BankAccountOverdraftInvalidException {

        BankAccount bankAccount = getAccount(dto.getAccountId());
        Customer customer = outputAccountService.loadRemoteCustomer(bankAccount.getCustomerId());
        validateRemoteCustomer(customer.getCustomerId(), customer.getState());
        if (bankAccount.getState().equals(BANK_ACCOUNT_SUSPEND)) {
            throw new BankAccountSuspendException(ExceptionMsg.BANK_ACCOUNT_STATE_SUSPENDED);
        }

        if (bankAccount instanceof SavingBankAccount account) {
            throw new BankAccountTypeNotAcceptedException(String.format(FORMATTER,
                    ExceptionMsg.BANK_ACCOUNT_TYPE_NOT_ACCEPTED, account));
        } else if (dto.getOverdraft() < 0) {
            throw new BankAccountOverdraftInvalidException(ExceptionMsg.BANK_ACCOUNT_OVERDRAFT);
        } else {
            CurrentBankAccount current = new CurrentBankAccount.CurrentAccountBuilder()
                    .overdraft(dto.getOverdraft())
                    .build();
            current.setAccountId(bankAccount.getAccountId());
            current.setType(bankAccount.getType());
            current.setState(bankAccount.getState());
            current.setBalance(bankAccount.getBalance());
            current.setCreatedAt(bankAccount.getCreatedAt());
            current.setCustomerId(bankAccount.getCustomerId());
            BankAccount savedCurrent = outputAccountService.changeOverdraft(current);
            savedCurrent.setCustomer(customer);
            return savedCurrent;
        }

    }

    @Override
    public BankAccount changeInterestRate(BankAccountInterestRateDto dto) throws BankAccountNotFoundException,
            BankAccountSuspendException, RemoteCustomerStateInvalidException, RemoteCustomerApiUnreachableException, BankAccountTypeNotAcceptedException {
        BankAccount bankAccount = getAccount(dto.getAccountId());
        Customer customer = outputAccountService.loadRemoteCustomer(bankAccount.getCustomerId());
        validateRemoteCustomer(customer.getCustomerId(), customer.getState());
        if (bankAccount.getState().equals(BANK_ACCOUNT_SUSPEND)) {
            throw new BankAccountSuspendException(ExceptionMsg.BANK_ACCOUNT_STATE_SUSPENDED);
        }
        if (bankAccount instanceof CurrentBankAccount account) {
            throw new BankAccountTypeNotAcceptedException(String.format(FORMATTER,
                    ExceptionMsg.BANK_ACCOUNT_TYPE_NOT_ACCEPTED, account));
        } else {
            SavingBankAccount saving = new SavingBankAccount.SavingAccountBuilder()
                    .interestRate(dto.getInterestRate())
                    .build();
            saving.setAccountId(bankAccount.getAccountId());
            saving.setType(bankAccount.getType());
            saving.setState(bankAccount.getState());
            saving.setBalance(bankAccount.getBalance());
            saving.setCreatedAt(bankAccount.getCreatedAt());
            saving.setCustomerId(bankAccount.getCustomerId());
            //call output adapter to save update account
            BankAccount savingBankAccount = outputAccountService.changeInterestRate(saving);
            savingBankAccount.setCustomer(bankAccount.getCustomer());
            return savingBankAccount;
        }
    }

    private void validateAccount(BankAccountDto dto) throws BankAccountTypeInvalidException,
            BankAccountFieldsInvalidException {
        BankAccountValidators.formatter(dto);

        if (BankAccountValidators.invalidFields(dto))
            throw new BankAccountFieldsInvalidException(ExceptionMsg.BANK_ACCOUNT_FIELDS_INVALID);
        if (!BankAccountValidators.validAccountSType(dto.getType()))
            throw new BankAccountTypeInvalidException(ExceptionMsg.BANK_ACCOUNT_TYPE_INVALID);
    }

    private void validateRemoteCustomer(String customerId, String customerState) throws RemoteCustomerApiUnreachableException,
            RemoteCustomerStateInvalidException {
        if (BankAccountValidators.remoteCustomerApiUnreachable(customerId))
            throw new RemoteCustomerApiUnreachableException(ExceptionMsg.REMOTE_CUSTOMER_API);
        else if (BankAccountValidators.remoteCustomerStateInvalid(customerState))
            throw new RemoteCustomerStateInvalidException(ExceptionMsg.REMOTE_CUSTOMER_STATE);
    }

    private Collection<BankAccount> setCustomerToAccount(Collection<BankAccount> bankAccounts) {
        return bankAccounts.stream()
                .map(account -> {
                    Customer remoteCustomer = outputAccountService.loadRemoteCustomer(account.getCustomerId());
                    account.setCustomer(remoteCustomer);
                    if (account instanceof CurrentBankAccount current) {
                        current.setType(BANK_ACCOUNT_CURRENT);
                    } else if (account instanceof SavingBankAccount saving) {
                        saving.setType(BANK_ACCOUNT_SAVING);
                    }
                    return account;
                })
                .toList();
    }
}
