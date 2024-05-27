import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { GetApisService } from "./getaway.apis";
import { Observable } from "rxjs";
import { Operation } from "../models/operation/operation.model";
import { bankAccountAppHeader } from "./header";
import { TransferDto } from "../models/operation/transfer-dto.model";
import { BankAccount } from "../models/bank-account/bank-account.model";

@Injectable({ providedIn: "root" })
export class OperationService {
    httpClient = inject(HttpClient);
    operationApi = new GetApisService().getOperationApi();

    getAllOperations(): Observable<Operation[]> {
        return this.httpClient.get<Operation[]>(`${this.operationApi}/operations`);
    }

    create(operation: Operation): Observable<Operation> {
        return this.httpClient.post<Operation>(`${this.operationApi}/operations`, operation,
         { headers: bankAccountAppHeader });
    }
    createTransfer(transferDto: TransferDto): Observable<Map<string, BankAccount>>{
        return this.httpClient.post<Map<string, BankAccount>>(`${this.operationApi}/operations/transfer`,transferDto,
            {headers:bankAccountAppHeader}
        );
    }
    getAllTransfers(): Observable<any[]>{
        return this.httpClient.get<any[]>(`${this.operationApi}/transfers`);
    }
}