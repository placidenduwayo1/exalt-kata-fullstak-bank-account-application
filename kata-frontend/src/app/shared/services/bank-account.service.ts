import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { Observable } from "rxjs";
import { BankAccount } from "../models/bank-account/bank-account.model";
import { GetApisService } from "./getaway.apis";

@Injectable({providedIn:"root"})
export class BankAccountService {
    httpClient = inject(HttpClient);
    bankAccountApi = new GetApisService().getBankAccountApi();

    getAll(): Observable<BankAccount[]>{
        return this.httpClient.get<BankAccount[]>(`${this.bankAccountApi}/accounts`)
    }
}