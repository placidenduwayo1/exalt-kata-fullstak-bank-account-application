import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { Observable } from "rxjs";
import { BankAccount } from "../models/bank-account/bank-account.model";
import { GetApisService } from "./getaway.apis";
import { bankAccountAppHeader } from "./header";
import { AccountSwitchStateDto } from "../models/bank-account/account-swithstate-dto.model";

@Injectable({ providedIn: "root" })
export class BankAccountService {

    httpClient = inject(HttpClient);
    bankAccountApi = new GetApisService().getBankAccountApi();

    getAll(): Observable<BankAccount[]> {
        return this.httpClient.get<BankAccount[]>(`${this.bankAccountApi}/accounts`)
    }

    create(bankAccount: BankAccount): Observable<BankAccount> {
        return this.httpClient.post<BankAccount>(`${this.bankAccountApi}/accounts`, bankAccount,
            { headers: bankAccountAppHeader }
        )
    }
    getBankAcount(accountId: string): Observable<BankAccount> {
        return this.httpClient.get<BankAccount>(`${this.bankAccountApi}/accounts/${accountId}`);
    }

    switchState(accountSwitchStateDto: AccountSwitchStateDto): Observable<BankAccount> {
        return this.httpClient.post<BankAccount>(`${this.bankAccountApi}/accounts/switch-state`,accountSwitchStateDto,
            {headers: bankAccountAppHeader}
         )
    }
}