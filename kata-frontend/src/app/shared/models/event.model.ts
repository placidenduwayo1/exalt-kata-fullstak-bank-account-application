export enum CustomerEvent {
    CUSTOMER_GET_ALL = "[Customer-Api] GET ALL CUSTOMERS EVENT",
    CUSTOMER_CREATE = "[Customer-Api] CUSTOMER CREATE EVENT",
    REFRESH = "REFRESH TABLE DATA"
}
export enum BankAccountEvent {
    BANK_ACCOUNT_GET_ALL ="[Bank-Account-Api] GET ALL BANK ACCOUNTS EVENT",
    BANK_ACCOUNT_CREATE = "[Bank-Account-Api] CREATE A NEW BANK ACCOUNT",
    REFRESH = "REFRESH TABLE DATA"
}

export enum OperationEvent {
    OPERATION_GET_ALL = "[Operation-Api] GET ALL OPERATIONS EVENT",
    OPERATION_CREATE_NEW = "[Operation-Api] CREATE NEW OPERATION"
}