import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { GetApisService } from "./getaway.apis";
import { Observable } from "rxjs";
import { Operation } from "../models/operation.model";

@Injectable({ providedIn: "root" })
export class OperationService {
    httpClient = inject(HttpClient);
    operationApi = new GetApisService().getOperationApi();

    getAll():Observable<Operation[]>{
       return this.httpClient.get<Operation[]>(`${this.operationApi}/operations`);
    }
}