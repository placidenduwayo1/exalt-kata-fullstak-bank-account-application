package fr.exalt.businessmicroservicecustomer.domain.usecase;

import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.*;
import fr.exalt.businessmicroservicecustomer.domain.ports.output.OutputCustomerService;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.AddressDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.CustomerDto;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.Request;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.RequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
class InputCustomerImplTest {

    @Mock
    private OutputCustomerService mock;
    @InjectMocks
    private InputCustomerImpl underTest;
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
    private final Customer customer = MapperService.fromTo(customerDto);
    private final Address address = MapperService.fromTo(addressDto);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCustomer() throws CustomerStateInvalidException, CustomerOneOrMoreFieldsInvalidException,
            CustomerEmailInvalidException, CustomerAlreadyExistsException {
        // PREPARE
        RequestDto requestDto = RequestDto.builder()
                .addressDto(addressDto)
                .customerDto(customerDto)
                .build();
        Request request = Request.builder()
                .address(address)
                .customer(customer)
                .build();
        //EXECUTE
        when(mock.createAddress(any(Address.class))).thenReturn(address);
        when(mock.createCustomer(any(Customer.class), any(Address.class)))
                .thenReturn(request);
        Customer actual = underTest.createCustomer(requestDto);
        //VERIFY
        assertAll("", () -> {
            verify(mock, atLeast(1)).createAddress(any(Address.class));
            verify(mock, atLeast(1))
                    .createCustomer(any(Customer.class), any(Address.class));
            assertNotNull(actual);
        });
    }

    @Test
    void getAllCustomers() {
        //PREPARE
        Collection<Customer> customers = List.of(customer);
        //EXECUTE
        when(mock.getAllCustomers()).thenReturn(customers);
        Collection<Customer> actual = underTest.getAllCustomers();
        //VERIFY
        assertAll("", () -> {
            verify(mock, atLeast(1)).getAllCustomers();
            assertEquals(1, actual.size());
        });
    }

    @Test
    void getCustomer() throws CustomerNotFoundException {
        //PREPARE
        final String id = "1";
        //EXECUTE
        when(mock.getCustomer(id)).thenReturn(customer);
        Customer actual = underTest.getCustomer(id);
        //VERIFY
        assertAll("", () -> {
            verify(mock, atLeast(1)).getCustomer(id);
            assertNotNull(actual);
        });
    }

    @Test
    void getAddress() throws AddressNotFoundException {
        //PREPARE
        final String id = "1";
        //EXECUTE
        when(mock.getAddress(id)).thenReturn(address);
        Address actual = underTest.getAddress(id);
        //VERIFY
        assertAll("", () -> {
            verify(mock, atLeast(1)).getAddress(id);
            assertNotNull(actual);
        });


    }

    @Test
    void testGetAddress() {
        //PREPARE
        Address newAddress = new Address.AddressBuilder()
                .addressId("1")
                .streetNum(184)
                .streetName("avenue de liège")
                .build();
        //EXECUTE
        when(mock.getAddress(addressDto)).thenReturn(address);
        Address actual = underTest.getAddress(addressDto);
        //VERIFY
        assertAll("", () -> {
            verify(mock, atLeast(1)).getAddress(addressDto);
            assertNotNull(actual);
            assertNotEquals(newAddress.toString(), address.toString());
        });
    }

    @Test
    void getAllAddresses() {
        //PREPARE
        Collection<Address> addresses = List.of(address);
        //EXECUTE
        when(mock.getAllAddresses()).thenReturn(addresses);
        Collection<Address> actual = underTest.getAllAddresses();
        //VERIFY
        assertAll("", () -> {
            verify(mock, atLeast(1)).getAllAddresses();
            assertNotEquals(2, actual.size());
            assertFalse(actual.isEmpty());
        });
    }

    @Test
    void updateCustomer() throws CustomerNotFoundException, CustomerStateInvalidException, CustomerOneOrMoreFieldsInvalidException,
            CustomerEmailInvalidException, CustomerAlreadyExistsException {
        //PREPARE
        final String id = "1";
        RequestDto requestDto = RequestDto.builder()
                .customerDto(customerDto)
                .addressDto(addressDto)
                .build();
        Request request = Request.builder()
                .customer(customer)
                .address(address)
                .build();
        //EXECUTE
        when(mock.getCustomer(id)).thenReturn(customer);
        when(mock.createAddress(any(Address.class))).thenReturn(address);
        when(mock.updateCustomer(any(Customer.class), any(Address.class)))
                .thenReturn(request);
        Customer actual = underTest.updateCustomer(id, requestDto);
        //VERIFY
        assertAll("", () -> {
            verify(mock, atLeast(1)).getCustomer(id);
            verify(mock, atLeast(1))
                    .updateCustomer(any(Customer.class), any(Address.class));
            assertNotNull(actual);
        });
    }

    @Test
    void updateAddress() throws AddressNotFoundException, AddressFieldsInvalidException {
        //PREPARE
        final String id ="1";
        //EXECUTE
        when(mock.getAddress(id)).thenReturn(address);
        when(mock.updateAddress(any(Address.class))).thenReturn(address);
        Address actual = underTest.updateAddress(id,addressDto);
        //VERIFY
        assertAll("",()->{
            verify(mock, atLeast(1)).getAddress(id);
            verify(mock, atLeast(1)).updateAddress(address);
            assertNotNull(actual);
        });

    }
}