package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.service;

import fr.exalt.businessmicroserviceoperation.domain.entities.Account;
import fr.exalt.businessmicroserviceoperation.domain.entities.Customer;
import fr.exalt.businessmicroserviceoperation.domain.entities.Operation;
import fr.exalt.businessmicroserviceoperation.domain.ports.output.OutputOperationService;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.AccountDto;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.AccountModel;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.CustomerModel;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.proxies.RemoteAccountServiceProxy;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.proxies.RemoteCustomerServiceProxy;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.OperationModel;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OutputOperationServiceImpl implements OutputOperationService {
    private final RemoteAccountServiceProxy remoteAccountServiceProxy;
    private final RemoteCustomerServiceProxy remoteCustomerServiceProxy;
    private final OperationRepository operationRepository;

    public OutputOperationServiceImpl(@Qualifier(value = "accountserviceproxy") RemoteAccountServiceProxy remoteAccountServiceProxy,
                                      @Qualifier(value = "customerserviceproxy") RemoteCustomerServiceProxy remoteCustomerServiceProxy,
                                      OperationRepository operationRepository) {
        this.remoteAccountServiceProxy = remoteAccountServiceProxy;
        this.remoteCustomerServiceProxy = remoteCustomerServiceProxy;
        this.operationRepository = operationRepository;
    }

    @Override
    public Operation createOperation(Operation operation) {
        OperationModel model = operationRepository.save(MapperService.fromTo(operation));
        return MapperService.fromTo(model);
    }

    @Override
    public Collection<Operation> getAllOperations() {
        return operationRepository.findAll().stream()
                .map(MapperService::fromTo)
                .toList();
    }

    @Override
    public Account loadRemoteAccount(String accountId) {
        AccountModel model = remoteAccountServiceProxy.loadRemoteAccount(accountId);
        return MapperService.fromTo(model);
    }

    @Override
    public Customer loaddRemoteCustomer(String customerId) {
        CustomerModel model = remoteCustomerServiceProxy.loadRemoteCustomer(customerId);
        return MapperService.fromTo(model);
    }

    @Override
    public Account updateRemoteAccount(String accountId, AccountDto accountDto) {
        AccountModel model = remoteAccountServiceProxy.updateRemoteAccount(accountId, accountDto);
        return MapperService.fromTo(model);
    }
}
