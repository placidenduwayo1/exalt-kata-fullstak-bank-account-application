package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class ExceptionMsg {
    private ExceptionMsg() {
    }
    public static final String ACCOUNT_TYPE_INVALID = "Account type invalid Exception";
    public static final String ACCOUNT_FIELDS_INVALID = "Account one or more fields invalid Exception";
    public static final String REMOTE_CUSTOMER_API = "It may be possible remote Customer Api unreachable Exception";
    public static final String REMOTE_CUSTOMER_STATE = "Remote Customer has been archived, he can not be assigned an account Exception";
    public static final String ACCOUNT_NOT_FOUND = "Account not found Exception";
}