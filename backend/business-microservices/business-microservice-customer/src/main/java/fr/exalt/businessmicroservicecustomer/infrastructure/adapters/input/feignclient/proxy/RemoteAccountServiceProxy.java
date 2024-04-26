package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.input.feignclient.proxy;

import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.input.feignclient.model.AccountModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.List;

@FeignClient(name = "business-microservice-bank-account",
        path = "/api-bank-account",
        fallback = RemoteAccountServiceProxy.RemoteAccountServiceFallback.class)
@Qualifier(value = "accountserviceproxy")
public interface RemoteAccountServiceProxy {
    @GetMapping(value = "/customers/{customerId}/accounts")
    Collection<AccountModel> loadRemoteAccountsOfCustomer(@PathVariable(name = "customerId") String customerId);

    // following class is an implementation of fallback if remote account is unreachable using resilience 4j
    @Component
    @Slf4j
    class RemoteAccountServiceFallback implements RemoteAccountServiceProxy{

        @Override
        public Collection<AccountModel> loadRemoteAccountsOfCustomer(String customerId) {
            final String unreachable ="It may be possible remote account api is unreachable";
            AccountModel resilience = AccountModel.builder()
                    .accountId(unreachable)
                    .balance(-1)
                    .overdraft(-1)
                    .createdAt(unreachable)
                    .build();
            log.error("[Fallback] {}",resilience);
            return List.of(resilience);
        }
    }
}
