import { Address } from "./address.model";
import { Customer } from "./customer.model";

export class Request {
    customer!: Customer
    address!: Address;
}