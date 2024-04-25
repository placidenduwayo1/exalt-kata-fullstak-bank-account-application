package fr.exalt.businessmicroserviceoperation.domain.exceptions;

public class ExceptionsMsg {
    private ExceptionsMsg(){}
    public static final String REMOTE_ACCOUNT_UNREACHABLE = "it may be possible that remote account is unreachable Exception";
    public static final String OPERATION_REQUEST_FIELDS ="Operation one or more fields invalid Exception";
    public static final String OPERATION_TYPE = "Operation type invalid Exception";
    public static final String REMOTE_ACCOUNT_BALANCE = "Remote Account Balance not enough Exception";
    public static final String REMOTE_CUSTOMER_UNREACHABLE ="it may be possible that remote customer is unreachable Exception";
}
