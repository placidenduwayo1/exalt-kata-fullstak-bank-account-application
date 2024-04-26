package fr.exalt.businessmicroservicecustomer.domain.usecase;

import fr.exalt.businessmicroservicecustomer.domain.entities.CustomerState;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.AddressDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.RequestDto;

public class CustomerValidators {
    private CustomerValidators() {
    }

    public static void formatter(RequestDto requestDto) {
        requestDto.getCustomerDto().setFirstname(requestDto.getCustomerDto().getFirstname().strip());
        requestDto.getCustomerDto().setLastname(requestDto.getCustomerDto().getLastname().strip());
        requestDto.getCustomerDto().setState(requestDto.getCustomerDto().getState().strip());
        requestDto.getAddressDto().setStreetName(requestDto.getAddressDto().getStreetName().strip());
        requestDto.getAddressDto().setCity(requestDto.getAddressDto().getCity().strip());
        requestDto.getAddressDto().setCountry(requestDto.getAddressDto().getCountry().strip());
    }

    public static void formatter(AddressDto addressDto) {
        addressDto.setStreetName(addressDto.getStreetName().strip());
        addressDto.setCity(addressDto.getCity().strip());
        addressDto.setCountry(addressDto.getCountry().strip());
    }

    public static boolean invalidAddressDto(AddressDto addressDto) {
        return addressDto.getStreetNum() < 1
                || addressDto.getStreetName().isBlank()
                || addressDto.getPoBox() < 1
                || addressDto.getCity().isBlank()
                || addressDto.getCountry().isBlank();
    }

    public static boolean invalidRequest(RequestDto dto) {
        return dto.getCustomerDto().getFirstname().isBlank()
                || dto.getCustomerDto().getLastname().isBlank()
                || dto.getCustomerDto().getState().isBlank()
                || dto.getAddressDto().getStreetNum() < 1
                || dto.getAddressDto().getStreetName().isBlank()
                || dto.getAddressDto().getPoBox() < 1
                || dto.getAddressDto().getCity().isBlank()
                || dto.getAddressDto().getCountry().isBlank();
    }

    public static boolean isValidCustomerState(String customerState) {
        boolean stateValid = false;
        for (CustomerState state : CustomerState.values()) {
            if (customerState.equals(state.getState())) {
                stateValid = true;
                break;
            }
        }
        return stateValid;
    }
}
