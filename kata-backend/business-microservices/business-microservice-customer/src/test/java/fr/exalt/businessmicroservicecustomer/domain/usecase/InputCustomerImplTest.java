package fr.exalt.businessmicroservicecustomer.domain.usecase;

import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.*;
import fr.exalt.businessmicroservicecustomer.domain.ports.output.OutputCustomerService;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.*;
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
    private static final String EMAIL = "placide.nd@gmail.com";
    private final CustomerDto customerDto = CustomerDto.builder()
            .firstname(FIRSTNAME)
            .lastname(LASTNAME)
            .email(EMAIL)
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
    private final RequestDto requestDto = RequestDto.builder()
            .addressDto(addressDto)
            .customerDto(customerDto)
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer.setState("active");
    }

    @Test
    void createCustomer() throws CustomerStateInvalidException, CustomerOneOrMoreFieldsInvalidException,
            CustomerEmailInvalidException, CustomerAlreadyExistsException {
        // PREPARE
       final Request request = Request.builder()
                .address(address)
                .customer(customer)
                .build();
        //EXECUTE
        when(mock.createAddress(any(Address.class))).thenReturn(address);
        when(mock.createCustomer(any(Customer.class), any(Address.class)))
                .thenReturn(request);
        final Customer actual = underTest.createCustomer(requestDto);
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
        final Collection<Customer> customers = List.of(customer);
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
        final Address actual = underTest.getAddress(id);
        //VERIFY
        assertAll("", () -> {
            verify(mock, atLeast(1)).getAddress(id);
            assertNotNull(actual);
        });


    }

    @Test
    void testGetAddress() {
        //PREPARE
        final Address newAddress = new Address.AddressBuilder()
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
        final Collection<Address> addresses = List.of(address);
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
        final RequestDto requestDto = RequestDto.builder()
                .customerDto(customerDto)
                .addressDto(addressDto)
                .build();
        final Request request = Request.builder()
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
        final String id = "1";
        //EXECUTE
        when(mock.getAddress(id)).thenReturn(address);
        when(mock.updateAddress(any(Address.class))).thenReturn(address);
        Address actual = underTest.updateAddress(id, addressDto);
        //VERIFY
        assertAll("", () -> {
            verify(mock, atLeast(1)).getAddress(id);
            verify(mock, atLeast(1)).updateAddress(address);
            assertNotNull(actual);
        });
    }

    @Test
    void archiveCustomer() throws CustomerStateInvalidException, CustomerNotFoundException, CustomerSameStateException, AddressNotFoundException {
        //PREPARE
        final CustomerSwitchActiveArchiveDto dto = CustomerSwitchActiveArchiveDto.builder()
                .customerId("id")
                .state("archive")
                .build();
        final Customer c = new Customer.CustomerBuilder()
                .customerId(customer.getCustomerId())
                .firstname(customer.getFirstname())
                .lastname(customer.getLastname())
                .createdAt(customer.getCreatedAt())
                .state("archive")
                .address(customer.getAddress())
                .email(customer.getEmail())
                .build();;
        //EXECUTE
        when(mock.getCustomer("id")).thenReturn(customer);
        when(mock.switchCustomerBetweenActiveArchive(customer)).thenReturn(c);
        Customer actual = underTest.switchCustomerBetweenActiveArchive(dto);
        //VERIFY
        assertAll("", () -> {
            verify(mock, atLeast(1)).getCustomer("id");
            verify(mock, atLeast(1)).switchCustomerBetweenActiveArchive(any(Customer.class));
            assertNotNull(actual);
            assertEquals("archive", actual.getState());
        });
    }

    @Test
    void testCustomerBusinessExceptions() {
        final RequestDto dto1 = RequestDto.builder()
                .addressDto(addressDto)
                .customerDto(customerDto)
                .build();
        dto1.getCustomerDto().setEmail("");
        InputCustomerImpl inputCustomerBusinessExceptions = new InputCustomerImpl(mock);
        Exception exception1 = assertThrows(CustomerOneOrMoreFieldsInvalidException.class, () -> {
            inputCustomerBusinessExceptions.createCustomer(dto1);
        });

        final RequestDto dto2 = RequestDto.builder()
                .addressDto(addressDto)
                .customerDto(customerDto)
                .build();
        dto2.getCustomerDto().setEmail("placide.nd");

        Exception exception2 = assertThrows(CustomerEmailInvalidException.class, () -> {
            inputCustomerBusinessExceptions.createCustomer(dto2);
        });
        final RequestDto dto3 = RequestDto.builder()
                .addressDto(addressDto)
                .customerDto(CustomerDto.builder()
                        .firstname(FIRSTNAME)
                        .lastname(LASTNAME)
                        .email(EMAIL)
                        .build())
                .build();

        Exception exception3 = assertThrows(CustomerAlreadyExistsException.class, () -> {
            when(mock.getCustomer(dto3.getCustomerDto())).thenReturn(customer);
            inputCustomerBusinessExceptions.createCustomer(dto3);
        });

        final RequestDto dto4 = RequestDto.builder()
                .addressDto(AddressDto.builder()
                        .streetNum(-1)
                        .streetName("")
                        .poBox(-1)
                        .city("")
                        .country("")
                        .build())
                .customerDto(customerDto)
                .build();

        Exception exception4 = assertThrows(AddressFieldsInvalidException.class, () -> {
            when(mock.getAddress(address.getAddressId())).thenReturn(address);
            inputCustomerBusinessExceptions.updateAddress(address.getAddressId(), dto4.getAddressDto());
        });

        final CustomerSwitchActiveArchiveDto dto5 = CustomerSwitchActiveArchiveDto.builder()
                .state("unknown")
                .customerId("id")
                .build();
        Exception exception5 = assertThrows(CustomerStateInvalidException.class, ()->{
            when(mock.getCustomer(dto5.getCustomerId())).thenReturn(customer);
            inputCustomerBusinessExceptions.switchCustomerBetweenActiveArchive(dto5);
        });

        final Customer customer1 = new Customer.CustomerBuilder()
                .customerId(customer.getCustomerId())
                .firstname(customer.getFirstname())
                .lastname(customer.getLastname())
                .createdAt(customer.getCreatedAt())
                .state("archive")
                .address(customer.getAddress())
                .email(customer.getEmail())
                .build();
        final CustomerSwitchActiveArchiveDto dto6 = CustomerSwitchActiveArchiveDto.builder()
                .state("archive")
                .customerId("id")
                .build();
        Exception exception6 = assertThrows(CustomerSameStateException.class, ()->{
           when(mock.getCustomer(dto6.getCustomerId())).thenReturn(customer1);
           inputCustomerBusinessExceptions.switchCustomerBetweenActiveArchive(dto6);
        });

        assertAll("exceptions", () -> {
            assertNotNull(exception1);
            assertNotNull(exception2);
            assertNotNull(exception3);
            assertNotNull(exception4);
            assertNotNull(exception5);
            assertNotNull(exception6);
        });
    }
}