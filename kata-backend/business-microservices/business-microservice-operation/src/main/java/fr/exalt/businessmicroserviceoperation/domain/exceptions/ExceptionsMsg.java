package fr.exalt.businessmicroserviceoperation.domain.exceptions;

public class ExceptionsMsg {
    private ExceptionsMsg(){}
    public static final String REMOTE_ACCOUNT_UNREACHABLE = "it may be possible that remote bank account is unreachable Exception";
    public static final String OPERATION_REQUEST_FIELDS ="Operation one or more fields invalid Exception";
    public static final String OPERATION_TYPE = "Operation type invalid Exception";
    public static final String REMOTE_ACCOUNT_BALANCE = "Remote bank account Balance not enough Exception";
    public static final String REMOTE_CUSTOMER_UNREACHABLE ="it may be possible that remote customer is unreachable Exception";
    public static final String REMOTE_ACCOUNT_NOT_ACCESSIBLE_FROM_OUTSIDE = "Remote bank account type is inaccessible " +
            "from outside Exception";
    public static final String REMOTE_CUSTOMER_STATE ="Remote customer state Invalid Exception";
    public static final String REMOTE_BANK_ACCOUNT_SUSPENDED = "Remote bank account suspended Exception";
    public static final String REMOTE_ACCOUNT_SUSPENDED = "Remote Bank Account is suspended Exception";
}
