package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.proxies;

import fr.exalt.businessmicroserviceoperation.domain.exceptions.ExceptionsMsg;
import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.input.feignclient.models.CustomerModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "business-microservice-customer",
        path = "/api-customer",
        fallback = RemoteCustomerServiceProxy.RemoteCustomerServiceFallback.class)
@Qualifier(value = "customerserviceproxy")
public interface RemoteCustomerServiceProxy {
    @GetMapping(value = "/customers/{customerId}")
    CustomerModel loadRemoteCustomer(@PathVariable(name = "customerId") String customerId);

    @Component
    @Slf4j
    class RemoteCustomerServiceFallback implements RemoteCustomerServiceProxy {
        @Override
        public CustomerModel loadRemoteCustomer(String customerId) {
            CustomerModel resilience = CustomerModel.builder()
                    .customerId(ExceptionsMsg.REMOTE_CUSTOMER_UNREACHABLE)
                    .firstname(ExceptionsMsg.REMOTE_CUSTOMER_UNREACHABLE)
                    .lastname(ExceptionsMsg.REMOTE_CUSTOMER_UNREACHABLE)
                    .state(ExceptionsMsg.REMOTE_ACCOUNT_UNREACHABLE)
                    .email(ExceptionsMsg.REMOTE_CUSTOMER_UNREACHABLE)
                    .build();
            log.error("[Fallback] customer load {}", resilience);
            return resilience;
        }
    }
}
