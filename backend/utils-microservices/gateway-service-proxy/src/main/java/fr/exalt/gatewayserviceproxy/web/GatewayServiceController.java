package fr.exalt.gatewayserviceproxy.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GatewayServiceController {
    private static final String MSG="welcome to gateway service proxy for routing user requests";
    private static final String API="gateway-api";
    @GetMapping("")
    public Map<String, String> welcome(){
        return Map.of(API, MSG);
    }
}
