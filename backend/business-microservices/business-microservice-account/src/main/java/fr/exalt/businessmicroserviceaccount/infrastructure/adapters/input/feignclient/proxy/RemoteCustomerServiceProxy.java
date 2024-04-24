package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.feignclient.proxy;

import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.input.feignclient.models.CustomerModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "business-microservice-customer", fallback = RemoteCustomerServiceProxy.RemoteCustomerFallback.class)
public interface RemoteCustomerServiceProxy {
    @GetMapping(value = "/customers/{customerId}")
    CustomerModel loadRemoteCustomer(@PathVariable(name = "customerId") String customerId);

    // following class is an implementation of fallback if remote customer is unreachable using resilience 4j
    @Component
    @Slf4j
    class RemoteCustomerFallback implements RemoteCustomerServiceProxy {
        @Override
        public CustomerModel loadRemoteCustomer(String customerId) {
            final String unreachable = "it is possible that remote customer is unreachable";
            CustomerModel resilience = CustomerModel.builder()
                    .customerId(unreachable)
                    .firstname(unreachable)
                    .lastname(unreachable)
                    .state(unreachable)
                    .build();
            log.error("[Fallback] resilience management {}", resilience);
            return resilience;
        }
    }
}
