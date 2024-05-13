package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.feignclient.proxy;

import fr.exalt.businessmicroserviceaccount.domain.exceptions.ExceptionMsg;
import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.feignclient.models.CustomerModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "business-microservice-customer",
        path = "/api-customer",
        fallback = RemoteCustomerServiceProxy.RemoteCustomerFallback.class)
@Qualifier(value = "remoteCustomerService")
public interface RemoteCustomerServiceProxy {
    @GetMapping(value = "/customers/{customerId}")
    CustomerModel loadRemoteCustomer(@PathVariable(name = "customerId") String customerId);

    // following class is an implementation of fallback if remote customer is unreachable using resilience 4j
    @Component
    @Slf4j
    class RemoteCustomerFallback implements RemoteCustomerServiceProxy {
        @Override
        public CustomerModel loadRemoteCustomer(String customerId) {
            CustomerModel resilience = CustomerModel.builder()
                    .customerId(ExceptionMsg.REMOTE_CUSTOMER_API)
                    .firstname(ExceptionMsg.REMOTE_CUSTOMER_API)
                    .lastname(ExceptionMsg.REMOTE_CUSTOMER_API)
                    .email(ExceptionMsg.REMOTE_CUSTOMER_API)
                    .state(ExceptionMsg.REMOTE_CUSTOMER_API)
                    .build();
            log.error("[Fallback] resilience management {}", resilience);
            return resilience;
        }
    }
}
