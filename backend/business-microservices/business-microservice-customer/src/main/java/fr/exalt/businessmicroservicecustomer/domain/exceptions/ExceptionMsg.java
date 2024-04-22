package fr.exalt.businessmicroservicecustomer.domain.exceptions;

public class ExceptionMsg {

    private ExceptionMsg(){}
    public static final String CUSTOMER_STATE_INVALID= "Customer State invalid Exception";
    public static final String CUSTOMER_FIELD_INVALID = "Customer Fields, one or more fields invalid Exception";
    public static final String CUSTOMER_NOT_FOUND = "Customer not found Exception";
}
