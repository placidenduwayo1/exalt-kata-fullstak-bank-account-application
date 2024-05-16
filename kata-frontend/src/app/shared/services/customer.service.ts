import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { Observable } from "rxjs";
import { Customer } from "../models/customer/customer.model";
import { Request } from "../models/customer/request.model";
import { GetApisService } from "./getaway.apis";


@Injectable({ providedIn: "root" })
export class CustomerService {
    httpClient = inject(HttpClient)
    customerApi = new GetApisService().getCustomerApi();

    getAll(): Observable<Customer[]> {
        return this.httpClient.get<Customer[]>(`${this.customerApi}/customers`);
    }
    create(request: Request): Observable<Customer> {
        return this.httpClient.post<Customer>(`${this.customerApi}/customers`, request);
    }
}