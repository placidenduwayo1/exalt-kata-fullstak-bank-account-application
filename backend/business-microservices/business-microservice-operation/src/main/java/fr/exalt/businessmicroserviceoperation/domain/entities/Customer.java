package fr.exalt.businessmicroserviceoperation.domain.entities;

public class Customer {
    private  String customerId;
    private  String firstname;
    private  String lastname;
    private  String state;
    private  String email;

    private Customer(CustomerBuilder builder) {
        this.customerId = builder.customerId;
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.state = builder.state;
        this.email = builder.email;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getState() {
        return state;
    }

    public String getEmail() {
        return email;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static class CustomerBuilder {
        private String customerId;
        private String firstname;
        private String lastname;
        private String state;
        private String email;

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

        public CustomerBuilder email(String email) {
            this.email = email;
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
                ", email='" + email + '\'' +
                ']';
    }
}
