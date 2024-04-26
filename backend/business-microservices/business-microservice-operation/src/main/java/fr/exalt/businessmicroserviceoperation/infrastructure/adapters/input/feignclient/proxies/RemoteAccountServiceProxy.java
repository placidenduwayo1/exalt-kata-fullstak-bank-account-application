package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.proxies;

import fr.exalt.businessmicroserviceoperation.domain.exceptions.ExceptionsMsg;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.BankAccountDto;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.BankAccountModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "business-microservice-bank-account",
        path = "/api-bank-account",
        fallback = RemoteAccountServiceProxy.RemoteAccountServiceFallback.class)
@Qualifier(value = "accountserviceproxy")
public interface RemoteAccountServiceProxy {
    @GetMapping(value = "/accounts/{accountId}")
    BankAccountModel loadRemoteAccount(@PathVariable(name = "accountId") String accountId);
    @PutMapping(value = "/accounts/{accountId}")
    BankAccountModel updateRemoteAccount(@PathVariable(name = "accountId") String accountId, @RequestBody BankAccountDto bankAccountDto);

    // this following class is an implementation of a fallback for resilience using resilience 4j
    @Component
    @Slf4j
    class RemoteAccountServiceFallback implements RemoteAccountServiceProxy {
        @Override
        public BankAccountModel loadRemoteAccount(String accountId) {
            BankAccountModel resilience = getResilience();
            log.error("[Fallback] load remote {}", resilience);
            return resilience;
        }

        @Override
        public BankAccountModel updateRemoteAccount(String accountId, BankAccountDto bankAccountDto) {
            BankAccountModel resilience = getResilience();
            log.error("[Fallback] update remote {}", resilience);
            return resilience;
        }

        private BankAccountModel getResilience(){
            return BankAccountModel.builder()
                    .accountId(ExceptionsMsg.REMOTE_ACCOUNT_UNREACHABLE)
                    .type(ExceptionsMsg.REMOTE_ACCOUNT_UNREACHABLE)
                    .balance(-1)
                    .overdraft(-1)
                    .build();
        }
    }
}
