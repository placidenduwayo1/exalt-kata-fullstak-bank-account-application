import { ResolveFn } from "@angular/router";
import { BankAccount } from "../models/bank-account/bank-account.model";
import { inject } from "@angular/core";
import { BankAccountService } from "./bank-account.service";
import { Customer } from "../models/customer/customer.model";
import { CustomerService } from "./customer.service";
import { Operation } from "../models/operation.model";
import { OperationService } from "./operation.service";

export const getAllBankAccountsResolve: ResolveFn<BankAccount[]> = ()=>{
    return inject(BankAccountService).getAll();
}

export const getAllCustomersResolve: ResolveFn<Customer[]> = ()=>{
    return inject(CustomerService).getAll();
}
export const getAllOperationsResolve: ResolveFn<Operation[]> = ()=>{
    return inject(OperationService).getAll();
}