import { Observable, Subject } from "rxjs";
import { Injectable } from "@angular/core";
import { BankAccountEvent, CustomerEvent, OperationEvent } from "../models/event.model";
@Injectable({providedIn: "root"})
export class CustomerEventService {
    private customerEventSubject: Subject<CustomerEvent> = new Subject<CustomerEvent>();
    public customerEventObservable: Observable<CustomerEvent> = this.customerEventSubject.asObservable();

    publishEvent(event: CustomerEvent) {
        this.customerEventSubject.next(event);
    }
}

@Injectable({providedIn: "root"})
export class BankAccountEventService {
    private bankAccountEventSubject: Subject<BankAccountEvent> = new Subject<BankAccountEvent>();
    public bankAccountEventObservable: Observable<BankAccountEvent> = this.bankAccountEventSubject.asObservable();

    publishEvent(event: BankAccountEvent) {
        this.bankAccountEventSubject.next(event);
    }
}

@Injectable({providedIn: "root"})
export class OperationEventService {
    private operationEventSubject: Subject<OperationEvent> = new Subject();
    public operationEventObservable: Observable<OperationEvent> = this.operationEventSubject.asObservable();

    publishEvent(event: OperationEvent) {
        this.operationEventSubject.next(event);
    }
}