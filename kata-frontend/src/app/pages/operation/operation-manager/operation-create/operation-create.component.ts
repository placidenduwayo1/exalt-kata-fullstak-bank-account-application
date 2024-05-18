import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { acceptLabel, confirmMsg, detailMsg1, detailMsg2, rejectLabel, severity1, severity2 } from 'src/app/pages/confirmation.message';
import { BankAccount } from 'src/app/shared/models/bank-account/bank-account.model';
import { OperationEvent } from 'src/app/shared/models/event.model';
import { Operation } from 'src/app/shared/models/operation.model';
import { BankAccountService } from 'src/app/shared/services/bank-account.service';
import { OperationEventService } from 'src/app/shared/services/event.service.publisher';
import { OperationService } from 'src/app/shared/services/operation.service';

interface OperationType {
  type: string;
}

@Component({
  selector: 'app-operation-create',
  templateUrl: './operation-create.component.html',
  styleUrls: ['./operation-create.component.css']
})
export class OperationCreateComponent implements OnInit {
  route = inject(ActivatedRoute);
  accountId!: string;
  bankAccount!: BankAccount;
  fb = inject(FormBuilder);
  operationFormRequest!: FormGroup;
  accountService = inject(BankAccountService);
  operationEventService = inject(OperationEventService);
  confirmService = inject(ConfirmationService);
  messageService = inject(MessageService);
  operationService = inject(OperationService);

  types: OperationType[] = [
    { type: 'retrait' },
    { type: 'depot' }
  ];
  selectedType!: OperationType;

  ngOnInit(): void {
    this.accountId = this.route.snapshot.params['accountId'];
    this.accountService.getBankAcount(this.accountId).subscribe({
      next: (account: BankAccount) => {
        this.bankAccount = account;
        console.log(this.bankAccount);
      },
      error: (err) => {
        alert(`error of loading account ${err}`);
      },
      complete: () => {
        console.log('observable completed')
      }
    });

    this.operationFormRequest = this.fb.group({
      type: ['', Validators.required],
      mount: ['', Validators.required],
      account: [{ value: this.accountId, disabled: true }]
    });

    this.operationEventService.operationEventObservable.subscribe({
      next: (event: OperationEvent) => {
        if (event == OperationEvent.OPERATION_CREATE_NEW) {

          let operation: Operation = this.operationFormRequest.value
          operation.accountId = this.accountId;
          operation.type = this.operationFormRequest.get('type')?.value.type;
          this.confirmService.confirm({
            acceptLabel: acceptLabel,
            rejectLabel: rejectLabel,
            message: confirmMsg,
            acceptIcon: 'pi pi-exclamation-circle',
            accept: () => {
              this.operationService.create(operation).subscribe({
                next: (operation: Operation) => {
                  console.log(operation);
                  this.messageService.add({
                    key: 'key1',
                    severity: severity1,
                    detail: detailMsg1,
                    sticky: true
                  });
                },
                error: (err) => {
                  alert(`error of creating operation ${err}`);
                },
                complete: () => {
                  console.log('observable completed');
                }
              })
            },
            reject: () => {
              this.messageService.add({
                key: 'key1',
                severity: severity2,
                detail: detailMsg2,
                sticky: true
              });
            }
          });
        }
      },
      error: (err) => {
        alert(`error of loading operations event occured ${err}`);
      },
      complete: () => {
        console.log('obersvable completed');
      }
    })
  }

  createOperation() {
    this.operationEventService.publishEvent(OperationEvent.OPERATION_CREATE_NEW);
  }
}
