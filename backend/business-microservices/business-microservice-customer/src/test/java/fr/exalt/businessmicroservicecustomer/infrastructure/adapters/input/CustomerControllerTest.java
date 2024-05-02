package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.input;

import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.*;
import fr.exalt.businessmicroservicecustomer.domain.ports.input.InputCustomerService;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.AddressDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.CustomerDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.RequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {
    @Mock
    private InputCustomerService mock;
    @InjectMocks
    private CustomerController underTest;
    private static final String FIRSTNAME = "placide";
    private static final String LASTNAME = "nduwayo";
    private static final String STATE = "active";
    private static final String EMAIL = "placide.nd@gmail.com";
    private final CustomerDto customerDto = CustomerDto.builder()
            .firstname(FIRSTNAME)
            .lastname(LASTNAME)
            .email(EMAIL)
            .state(STATE)
            .build();
    private final AddressDto addressDto = AddressDto.builder()
            .streetNum(184)
            .streetName("avenue de liège")
            .poBox(59300)
            .city("Valenciennes")
            .country("france")
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCustomer() throws CustomerStateInvalidException, CustomerOneOrMoreFieldsInvalidException, CustomerEmailInvalidException,
            CustomerAlreadyExistsException {
        //PREPARE
        RequestDto dto = RequestDto.builder()
                .addressDto(addressDto)
                .customerDto(customerDto)
                .build();
        Customer customer = MapperService.fromTo(customerDto);
        //EXECUTE
        when(mock.createCustomer(dto)).thenReturn(customer);
        Customer actual = underTest.createCustomer(dto);
        //VERIFY
        assertAll("", () -> {
            verify(mock, atLeast(1)).createCustomer(dto);
            assertNotNull(actual);
        });
    }

    @Test
    void getAllCustomers() {
        //PREPARE
        Customer customer = MapperService.fromTo(customerDto);
        Collection<Customer> customers = List.of(customer);
        //EXECUTE
        when(mock.getAllCustomers()).thenReturn(customers);
        Collection<Customer> actual = underTest.getAllCustomers();
        //VERIFY
        assertAll("",()->{
            verify(mock, atLeast(1)).getAllCustomers();
            assertEquals(1, actual.size());
        });
    }

    @Test
    void getAllAddresses() {
        //PREPARE
        Address address = MapperService.fromTo(addressDto);
        Collection<Address> addresses = List.of(address);
        //EXECUTE
        when(mock.getAllAddresses()).thenReturn(addresses);
        Collection<Address> actual = underTest.getAllAddresses();
        //VERIFY
        assertAll("",()->{
            verify(mock, atLeast(1)).getAllAddresses();
            assertNotNull(actual);
            assertEquals(1,actual.size());
        });

    }

    @Test
    void updateCustomer() throws CustomerStateInvalidException, CustomerOneOrMoreFieldsInvalidException, CustomerEmailInvalidException,
            CustomerNotFoundException, CustomerAlreadyExistsException {
        //PREPARE
        RequestDto dto = RequestDto.builder()
                .addressDto(addressDto)
                .customerDto(customerDto)
                .build();
        Customer customer = MapperService.fromTo(customerDto);
        final String id ="1";
        //EXECUTE
        when(mock.updateCustomer(id,dto)).thenReturn(customer);
        Customer actual = underTest.updateCustomer(id,dto);
        //VERIFY
        assertAll("", () -> {
            verify(mock, atLeast(1)).updateCustomer(id,dto);
            assertNotNull(actual);
        });
    }

    @Test
    void updateAddress() throws AddressNotFoundException, AddressFieldsInvalidException {
        //PREPARE
        final String id="1";
        Address address = MapperService.fromTo(addressDto);
        //EXECUTE
        when(mock.updateAddress(id,addressDto)).thenReturn(address);
        Address actual = underTest.updateAddress(id, addressDto);
        //VERIFY
        assertAll("",()->{
            verify(mock, atLeast(1)).updateAddress(id, addressDto);
            assertNotNull(actual);
        });
    }

    @Test
    void getCustomer() throws CustomerNotFoundException {
        //PREPARE
        final String id = "1";
        Customer customer = MapperService.fromTo(customerDto);
        //EXECUTE
        when(mock.getCustomer(id)).thenReturn(customer);
        Customer actual = underTest.getCustomer(id);
        //VERIFY
        assertAll("",()->{
            verify(mock, atLeast(1)).getCustomer(id);
            assertEquals(customer, actual);
            assertNotNull(actual);
        });
    }
}