package fr.exalt.businessmicroservicecustomer.domain.usecase;

import fr.exalt.businessmicroservicecustomer.domain.entities.State;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.RequestDto;

public class CustomerValidator {
    private CustomerValidator() {
    }

    public static void formatter(RequestDto dto) {
        dto.getCustomerDto().setFirstname(dto.getCustomerDto().getFirstname().strip());
        dto.getCustomerDto().setLastname(dto.getCustomerDto().getLastname().strip());
        dto.getCustomerDto().setState(dto.getCustomerDto().getState().strip());
        dto.getCustomerDto().setAddressId(dto.getCustomerDto().getAddressId().strip());
        dto.getAddressDto().setStreetName(dto.getAddressDto().getStreetName().strip());
        dto.getAddressDto().setCity(dto.getAddressDto().getCity().strip());
        dto.getAddressDto().setCountry(dto.getAddressDto().getCountry().strip());
    }

    public static boolean invalidRequest(RequestDto dto) {
        return dto.getCustomerDto().getFirstname().isBlank()
                || dto.getCustomerDto().getLastname().isBlank()
                || dto.getCustomerDto().getState().isBlank()
                || dto.getCustomerDto().getAddressId().isBlank()
                || dto.getAddressDto().getStreetNum()<1
                || dto.getAddressDto().getStreetName().isBlank()
                || dto.getAddressDto().getPoBox()<1
                || dto.getAddressDto().getCity().isBlank()
                || dto.getAddressDto().getCountry().isBlank();
    }

    public static boolean isValidCustomerState(String customerState) {
        boolean stateValid = false;
        for (State state : State.values()) {
            if (customerState.equals(state.getCustomerState())) {
                stateValid = true;
                break;
            }
        }
        return stateValid;
    }
}
