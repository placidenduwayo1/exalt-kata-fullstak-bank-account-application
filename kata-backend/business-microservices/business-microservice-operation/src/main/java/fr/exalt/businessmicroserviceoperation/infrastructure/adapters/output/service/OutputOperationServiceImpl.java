package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.service;

import fr.exalt.businessmicroserviceoperation.domain.entities.BankAccount;
import fr.exalt.businessmicroserviceoperation.domain.entities.Customer;
import fr.exalt.businessmicroserviceoperation.domain.entities.Operation;
import fr.exalt.businessmicroserviceoperation.domain.entities.TransferOperation;
import fr.exalt.businessmicroserviceoperation.domain.ports.output.OutputOperationService;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.BankAccountDto;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.BankAccountModel;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.CustomerModel;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.proxies.RemoteAccountServiceProxy;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.proxies.RemoteCustomerServiceProxy;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.OperationModel;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.TransferModel;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.repositories.OperationRepository;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.repositories.TransferRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OutputOperationServiceImpl implements OutputOperationService {
    private final RemoteAccountServiceProxy remoteAccountServiceProxy;
    private final RemoteCustomerServiceProxy remoteCustomerServiceProxy;
    private final OperationRepository operationRepository;
    private final TransferRepository transferRepository;

    public OutputOperationServiceImpl(@Qualifier(value = "accountserviceproxy") RemoteAccountServiceProxy remoteAccountServiceProxy,
                                      @Qualifier(value = "customerserviceproxy") RemoteCustomerServiceProxy remoteCustomerServiceProxy,
                                      OperationRepository operationRepository, TransferRepository transferRepository) {
        this.remoteAccountServiceProxy = remoteAccountServiceProxy;
        this.remoteCustomerServiceProxy = remoteCustomerServiceProxy;
        this.operationRepository = operationRepository;
        this.transferRepository = transferRepository;
    }

    @Override
    public Operation createOperation(Operation operation) {
        OperationModel model = operationRepository.save(MapperService.fromTo(operation));
        return MapperService.fromTo(model);
    }

    @Override
    public Collection<Operation> getAllOperations() {
        return mapToOperation(operationRepository.findAll());
    }

    @Override
    public BankAccount loadRemoteAccount(String accountId) {
        BankAccountModel model = remoteAccountServiceProxy.loadRemoteAccount(accountId);
        return MapperService.fromTo(model);
    }

    @Override
    public Customer loadRemoteCustomer(String customerId) {
        CustomerModel model = remoteCustomerServiceProxy.loadRemoteCustomer(customerId);
        return MapperService.fromTo(model);
    }

    @Override
    public BankAccount updateRemoteAccount(String accountId, BankAccountDto bankAccountDto) {
        BankAccountModel model = remoteAccountServiceProxy.updateRemoteAccount(accountId, bankAccountDto);
        return MapperService.fromTo(model);
    }

    @Override
    public Collection<Operation> getAccountOperations(String accountId) {
        return mapToOperation(operationRepository.findByAccountId(accountId));
    }

    @Override
    public void transfer(TransferOperation operation) {
        transferRepository.save(MapperService.fromTo(operation));
    }

    @Override
    public Collection<TransferOperation> getAllTransfer() {
        Collection<TransferModel> models = transferRepository.findAll();
        return models.stream().map(MapperService::fromTo).toList();
    }

    private Collection<Operation> mapToOperation(Collection<OperationModel> models){
        return models.stream()
                .map(MapperService::fromTo)
                .toList();
    }
}
