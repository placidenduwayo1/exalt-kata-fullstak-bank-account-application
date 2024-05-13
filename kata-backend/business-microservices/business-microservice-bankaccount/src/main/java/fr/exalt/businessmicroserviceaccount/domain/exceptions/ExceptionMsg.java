package fr.exalt.businessmicroserviceaccount.domain.exceptions;

public class ExceptionMsg {
    private ExceptionMsg() {
    }
    public static final String BANK_ACCOUNT_TYPE_INVALID = "Bank Account type invalid Exception";
    public static final String BANK_ACCOUNT_STATE_INVALID = "Bank Account state invalid Exception";
    public static final String BANK_ACCOUNT_FIELDS_INVALID = "Bank Account one or more fields invalid Exception";
    public static final String REMOTE_CUSTOMER_API = "It may be possible remote Customer Api unreachable Exception";
    public static final String REMOTE_CUSTOMER_STATE = "Remote Customer has been archived, he can not be assigned an account Exception";
    public static final String BANK_ACCOUNT_NOT_FOUND = "Bank Account not found Exception";
    public static final String BANK_ACCOUNT_SAME_STATE = "Bank Account state invalid, provide same state Exception";
    public static final String BANK_ACCOUNT_STATE_SUSPENDED = "Bank Account suspended Exception";
    public static final String BANK_ACCOUNT_TYPE_NOT_ACCEPTED = "Bank Account type cannot accepted this operation Exception";
    public static final String BANK_ACCOUNT_OVERDRAFT = "Bank Account overdraft invalid Exception";
}
