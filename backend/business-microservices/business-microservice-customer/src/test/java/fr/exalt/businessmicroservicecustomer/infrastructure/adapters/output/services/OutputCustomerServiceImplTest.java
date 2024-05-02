package fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.services;

import fr.exalt.businessmicroservicecustomer.domain.entities.Address;
import fr.exalt.businessmicroservicecustomer.domain.entities.Customer;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.AddressNotFoundException;
import fr.exalt.businessmicroservicecustomer.domain.exceptions.CustomerNotFoundException;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.mapper.MapperService;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.models.*;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.repositories.AddressRepository;
import fr.exalt.businessmicroservicecustomer.infrastructure.adapters.output.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class OutputCustomerServiceImplTest {
    @Mock
    private CustomerRepository mock1;
    @Mock
    private AddressRepository mock2;
    @InjectMocks
    private OutputCustomerServiceImpl underTest;
    private static final String CUSTOMER_ID = "1";
    private static final String FIRSTNAME = "placide";
    private static final String LASTNAME = "nduwayo";
    private static final String STATE = "active";
    private static final String EMAIL = "placide.nd@gmail.com";
    private final CustomerModel customer = CustomerModel.builder()
            .customerId(CUSTOMER_ID)
            .firstname(FIRSTNAME)
            .lastname(LASTNAME)
            .email(EMAIL)
            .state(STATE)
            .build();
    private final AddressModel address = AddressModel.builder()
            .addressId("1")
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
    void createCustomer() {
        //PREPARE
        Customer c = MapperService.fromTo(customer);
        Address a = MapperService.fromTo(address);
        Request request = Request.builder()
                .address(a)
                .customer(c)
                .build();
        //EXECUTE
       when(mock2.save(any(AddressModel.class))).thenReturn(address);
       when(mock1.save(any(CustomerModel.class))).thenReturn(customer);
       Request actual = underTest.createCustomer(c,a);
        //VERIFY
        assertAll("",()->{
           verify(mock2, atLeast(1)).save(any(AddressModel.class));
           verify(mock1, atLeast(1)).save(any(CustomerModel.class));
           assertEquals(request.toString(),actual.toString());
        });
    }

    @Test
    void getAddress() throws AddressNotFoundException {
        //PREPARE
        final String id="1";
        //EXECUTE
        when(mock2.findById(id)).thenReturn(Optional.of(address));
        Address actual = underTest.getAddress(id);
        //VERIFY
        assertAll("",()->{
            verify(mock2, atLeast(1)).findById(id);
            assertNotNull(actual);
        });
    }

    @Test
    void testGetAddress() {
        //PREPARE
        AddressDto dto = AddressDto.builder()
                .streetNum(184)
                .streetName("avenue de liège")
                .poBox(59300)
                .city("valenciennes")
                .country("france")
                .build();
        //EXECUTE
        when(mock2.findByStreetNumAndStreetNameAndPoBoxAndCityAndCountry(
                184, "avenue de liège",59300, "valenciennes","france")).thenReturn(address);
        Address actual = underTest.getAddress(dto);
        //VERIFY
        assertAll("",()->{
            verify(mock2, atLeast(1)).findByStreetNumAndStreetNameAndPoBoxAndCityAndCountry(
                    184, "avenue de liège",59300, "valenciennes","france");
            assertEquals(MapperService.fromTo(address).toString(),actual.toString());
        });
    }

    @Test
    void getAllCustomers() {
        //PREPARE
        List<CustomerModel> models = List.of(customer);
        Collection<Customer> customers = models.stream()
                .map(model -> {
                    model.setAddress(address);
                    return model;
                })
                .map(MapperService::fromTo)
                .toList();
        //EXECUTE
        when(mock1.findAll()).thenReturn(models);
        Collection<Customer> actual = underTest.getAllCustomers();
        //VERIFY
        assertAll("",()->{
            verify(mock1, atLeast(1)).findAll();
            assertEquals(customers.size(),actual.size());
            assertFalse(actual.isEmpty());
        });
    }

    @Test
    void getCustomer() throws CustomerNotFoundException {
        //PREPARE
        final String id = "1";
        CustomerModel model = CustomerModel.builder()
                .address(address)
                .build();
        //EXECUTE
        when(mock1.findById(id)).thenReturn(Optional.of(model));
        Customer actual = underTest.getCustomer(id);
        //VERIFY
        assertAll("",()->{
            verify(mock1, atLeast(1)).findById(id);
            assertNotNull(actual);
        });
    }

    @Test
    void createAddress() {
        //PREPARE
        Address addr = MapperService.fromTo(address);
        //EXECUTE
        when(mock2.save(any(AddressModel.class))).thenReturn(address);
        Address actual = underTest.createAddress(addr);
        //VERIFY
        assertAll("",()->{
            verify(mock2, atLeast(1)).save(any(AddressModel.class));
            assertNotNull(actual);
            assertEquals(addr.toString(),actual.toString());
        });
    }

    @Test
    void testGetCustomer() {
        //PREPARE
        CustomerDto dto = CustomerDto.builder()
                .firstname("placide")
                .lastname("nduwayo")
                .email("placide.nd@gmail.com")
                .state("active")
                .build();
        //EXECUTE
        when(mock1.findByFirstnameAndLastnameAndState(
                dto.getFirstname(), dto.getLastname(), dto.getState())).thenReturn(customer);
        Customer actual = underTest.getCustomer(dto);
        //VERIFY
        assertAll("",()->{
            verify(mock1, atLeast(1)).findByFirstnameAndLastnameAndState(
                    dto.getFirstname(), dto.getLastname(), dto.getState());
            assertNotNull(actual);
        });
    }

    @Test
    void getAllAddresses() {
        //PREPARE
        List<AddressModel> models = List.of(address);
        //EXECUTE
        when(mock2.findAll()).thenReturn(models);
        Collection<Address> actual = underTest.getAllAddresses();
        //VERIFY
        assertAll("",()->{
            verify(mock2, atLeast(1)).findAll();
            assertFalse(actual.isEmpty());
        });
    }

    @Test
    void updateCustomer() {
        //PREPARE
        Customer c = MapperService.fromTo(customer);
        Address a = MapperService.fromTo(address);
        Request request = Request.builder()
                .customer(c)
                .address(a)
                .build();
        //EXECUTE
        when(mock1.save(any(CustomerModel.class))).thenReturn(customer);
        when(mock2.save(any(AddressModel.class))).thenReturn(address);
        Request actual = underTest.updateCustomer(c, a);
        //VERIFY
        assertAll("",()->{
            verify(mock2, atLeast(1)).save(any(AddressModel.class));
            verify(mock1, atLeast(1)).save(any(CustomerModel.class));
            assertNotNull(actual);
            assertEquals(request.toString(),actual.toString());
        });
    }

    @Test
    void updateAddress() {
        //PREPARE
        Address a = MapperService.fromTo(address);
        //EXECUTE
        when(mock2.save(any(AddressModel.class))).thenReturn(address);
        Address actual = underTest.updateAddress(a);
        //VERIFY
        assertAll("",()->{
            verify(mock2, atLeast(1)).save(any(AddressModel.class));
            assertNotNull(actual);
        });
    }
}