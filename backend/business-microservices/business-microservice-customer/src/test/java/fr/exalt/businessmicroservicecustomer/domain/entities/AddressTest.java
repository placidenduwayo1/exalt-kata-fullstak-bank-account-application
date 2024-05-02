package fr.exalt.businessmicroservicecustomer.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressTest {
    private static final String ID="1";
    private static final int NUM=184;
    private static final String STREET="avenue de liège";
    private static final int POBOX=59300;
    private static final String CITY="valenciennes";
    private static final String COUNTRY="france";
    private final Address address1 = new Address.AddressBuilder()
            .addressId(ID)
            .streetNum(NUM)
            .streetName(STREET)
            .poBox(POBOX)
            .city(CITY)
            .country(COUNTRY)
            .build();
    private final String toStringAddress = "Address [" +
            "address-id='" + address1.getAddressId() + '\'' +
            ", street-num=" + address1.getStreetNum() +
            ", street-name='" + address1.getStreetName() + '\'' +
            ", Po Box=" + address1.getPoBox() +
            ", city='" + address1.getCity() + '\'' +
            ", country='" + address1.getCountry() + '\'' +
            ']';
    @Test
    void testGetAddress(){
        assertAll("",()->{
            assertEquals("1", address1.getAddressId());
            assertEquals(184, address1.getStreetNum());
            assertEquals("avenue de liège", address1.getStreetName());
            assertEquals(59300, address1.getPoBox());
            assertEquals("valenciennes", address1.getCity());
            assertEquals("france", address1.getCountry());
            assertEquals(toStringAddress,address1.toString());
        });
    }
    @Test
    void testSetAddress(){Address address2 = new Address.AddressBuilder().build();
        address2.setAddressId(ID);
        address2.setStreetNum(NUM);
        address2.setStreetName(STREET);
        address2.setPoBox(POBOX);
        address2.setCity(CITY);
        address2.setCountry(COUNTRY);
        assertEquals(address1.toString(), address2.toString());
    }
}