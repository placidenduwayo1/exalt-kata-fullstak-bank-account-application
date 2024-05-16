import { BankAccount } from "./bank-account/bank-account.model";

export class Operation {
    operationId!: string;
    type!: string;
    mount!: string;
    createdAt!: string;
    bankAccount!: BankAccount;
}