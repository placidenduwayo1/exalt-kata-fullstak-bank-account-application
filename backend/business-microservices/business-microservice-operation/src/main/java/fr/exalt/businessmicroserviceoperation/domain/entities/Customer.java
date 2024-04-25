package fr.exalt.businessmicroserviceoperation.domain.entities;

public class Customer {
    private String customerId;
    private String firstname;
    private String lastname;
    private String state;
    private String createdAt;

    private Customer(CustomerBuilder builder){
        this.customerId = builder.customerId;
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.customerId = builder.createdAt;
        this.state = builder.state;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static class CustomerBuilder {
        private String customerId;
        private String firstname;
        private String lastname;
        private String state;
        private String createdAt;

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

        public Customer build(){
            return new Customer(this);
        }
    }

    @Override
    public String toString() {
        return "Customer [" +
                "customer id ='" + customerId + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", state='" + state + '\'' +
                ", created at='" + createdAt + '\'' +
                ']';
    }
}