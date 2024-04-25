package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.proxies;

import fr.exalt.businessmicroserviceoperation.domain.exceptions.ExceptionsMsg;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.AccountDto;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.AccountModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "business-microservice-account",
        path = "/api-account",
        fallback = RemoteAccountServiceProxy.RemoteAccountServiceFallback.class)
@Qualifier(value = "accountserviceproxy")
public interface RemoteAccountServiceProxy {
    @GetMapping(value = "/accounts/{accountId}")
    AccountModel loadRemoteAccount(@PathVariable(name = "accountId") String accountId);
    @PutMapping(value = "/accounts/{accountId}")
    AccountModel updateRemoteAccount(@PathVariable(name = "accountId") String accountId, @RequestBody AccountDto accountDto);

    // this following class is an implementation of a fallback for resilience using resilience 4j
    @Component
    @Slf4j
    class RemoteAccountServiceFallback implements RemoteAccountServiceProxy {
        @Override
        public AccountModel loadRemoteAccount(String accountId) {
            AccountModel resilience = getResilience();
            log.error("[Fallback] load remote {}", resilience);
            return resilience;
        }

        @Override
        public AccountModel updateRemoteAccount(String accountId, AccountDto accountDto) {
            AccountModel resilience = getResilience();
            log.error("[Fallback] update remote {}", resilience);
            return resilience;
        }

        private AccountModel getResilience(){
            return AccountModel.builder()
                    .accountId(ExceptionsMsg.REMOTE_ACCOUNT_UNREACHABLE)
                    .type(ExceptionsMsg.REMOTE_ACCOUNT_UNREACHABLE)
                    .balance(-1)
                    .overdraft(-1)
                    .build();
        }
    }
}
