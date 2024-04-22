package fr.exalt.businessmicroservicecustomer.domain.entities;

public class Customer {
    private String customerId;
    private String firstname;
    private String lastname;
    private String state;
    private String createdAt;
    private String addressId;
    private Address address;

    public Customer(CustomerBuilder builder) {
        this.customerId = builder.addressId;
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.state = builder.state;
        this.createdAt = builder.createdAt;
        this.addressId = builder.addressId;
        this.address = builder.address;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public static class CustomerBuilder {
        private String customerId;
        private String firstname;
        private String lastname;
        private String state;
        private String createdAt;
        private String addressId;
        private Address address;

        public CustomerBuilder customerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public CustomerBuilder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public CustomerBuilder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public CustomerBuilder state(String state) {
            this.state = state;
            return this;
        }

        public CustomerBuilder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public CustomerBuilder addressId(String addressId) {
            this.addressId = addressId;
            return this;
        }

        public CustomerBuilder address(Address address) {
            this.address = address;
            return this;
        }

        public Customer build(){
            return new Customer(this);
        }
    }
}
