package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "business-microservice-account",
        path = "/api-account",
        fallback = RemoteAccountServiceProxy.RemoteAccountServiceFallback.class)
public interface RemoteAccountServiceProxy {
    @GetMapping(value = "/accounts/{accountId}")
    AccountModel loadRemoteAccount(@PathVariable(name = "accountId") String accountId);

    // this class implement a fallback for resilience using resilience 4j
    @Component
    @Slf4j
    class RemoteAccountServiceFallback implements RemoteAccountServiceProxy {
        @Override
        public AccountModel loadRemoteAccount(String accountId) {
            final String unreachable ="It may be possible account api is unreachable";
            AccountModel resilience = AccountModel.builder()
                    .accountId(unreachable)
                    .type(unreachable)
                    .balance(-1)
                    .overdraft(-1)
                    .build();
            log.error("[Fallback] {}", resilience);
            return resilience;
        }
    }
}
