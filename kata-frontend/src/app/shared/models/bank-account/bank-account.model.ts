import { Customer } from "../customer/customer.model";

export class BankAccount {
    accountId!: string;
    type!: string;
    state!: string;
    balance!: number;
    createdAt!: Date;
    customerId!: string;
}