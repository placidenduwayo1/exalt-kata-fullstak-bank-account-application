import { Address } from "./address.model";

export class Customer {
    customerId!: string;
    firstname!: string;
    lastname!: string;
    state!: string
    email!: string;
    createdAt!: Date;
    address!: Address;
}