package fr.exalt.businessmicroservicecustomer.domain.entities;

public class Address {
    private String addressId;
    private int streetNum;
    private String streetName;
    private int poBox;
    private String city;
    private String country;

    Address(AddressBuilder builder){
        this.addressId = builder.addressId;
        this.streetNum = builder.streetNum;
        this.streetName = builder.streetName;
        this.poBox = builder.poBox;
        this.city = builder.city;
        this.country = builder.country;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public int getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(int streetNum) {
        this.streetNum = streetNum;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getPoBox() {
        return poBox;
    }

    public void setPoBox(int poBox) {
        this.poBox = poBox;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address [" +
                "address-id='" + addressId + '\'' +
                ", street-num=" + streetNum +
                ", street-name='" + streetName + '\'' +
                ", Po Box=" + poBox +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ']';
    }

    public static class AddressBuilder {
        private String addressId;
        private int streetNum;
        private String streetName;
        private int poBox;
        private String city;
        private String country;

        public AddressBuilder addressId(String addressId) {
            this.addressId = addressId;
            return this;
        }

        public AddressBuilder streetNum(int streetNum) {
            this.streetNum = streetNum;
            return this;
        }

        public AddressBuilder streetName(String streetName) {
            this.streetName = streetName;
            return this;
        }

        public AddressBuilder poBox(int poBox) {
            this.poBox = poBox;
            return this;
        }

        public AddressBuilder city(String city) {
            this.city = city;
            return this;
        }

        public AddressBuilder country(String country) {
            this.country = country;
            return this;
        }
        public Address build(){
            return new Address(this);
        }
    }
}
