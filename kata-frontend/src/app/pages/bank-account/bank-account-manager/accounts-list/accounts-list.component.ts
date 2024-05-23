import { Component, Input, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { acceptLabel, confirmMsg, detailMsg1, detailMsg2, rejectLabel, severity1, severity2 } from 'src/app/pages/enum';
import { BankAccountInterestRateDto } from 'src/app/shared/models/bank-account/account-interest-rate-dto.model';
import { BankAccountOverdraftDto } from 'src/app/shared/models/bank-account/account-overdraft-dto.model';
import { AccountSwitchStateDto } from 'src/app/shared/models/bank-account/account-swithstate-dto.model';
import { BankAccount } from 'src/app/shared/models/bank-account/bank-account.model';
import { BankAccountEvent } from 'src/app/shared/models/event.model';
import { Operation } from 'src/app/shared/models/operation/operation.model';
import { TransferDto } from 'src/app/shared/models/operation/transfer-dto.model';
import { BankAccountService } from 'src/app/shared/services/bank-account.service';
import { BankAccountEventService, OperationEventService } from 'src/app/shared/services/event.service.publisher';
import { OperationService } from 'src/app/shared/services/operation.service';

interface OperationType {
  type: string;
}


@Component({
  selector: 'app-accounts-list',
  templateUrl: './accounts-list.component.html',
  styleUrls: ['./accounts-list.component.css']
})
export class AccountsListComponent implements OnInit {

  @Input() accountsList: BankAccount[] = [];
  router = inject(Router);
  bankAccountService = inject(BankAccountService);
  confirmService = inject(ConfirmationService);
  messageService = inject(MessageService);
  bankAccountEventService = inject(BankAccountEventService);
  operationEventService = inject(OperationEventService);

  operationTypes: OperationType[] = [{ type: 'depot' }, { type: 'retrait' }];
  operationService = inject(OperationService);


  getAccountStateSeverity(state: string): string {
    switch (state) {
      case 'active':
        return 'success';
      case 'suspended':
        return 'danger';
      default:
        return "danger";
    }
  }

  getCustomerStateSeverity(state: string): string {
    switch (state) {
      case 'active':
        return 'success';
      case 'archive':
        return 'danger';
      default:
        return "danger";
    }
  }

  getAccountTypeSeverity(type: string): string {
    switch (type) {
      case 'saving':
        return 'primary'
      case 'current':
        return 'warning'
      default:
        return 'danger'
    }
  }

  fb = inject(FormBuilder);
  operationFormRequest!: FormGroup;
  overdraftForm!: FormGroup;
  iRateForm!: FormGroup;
  transferFormRequest!: FormGroup;
  sidebarVisible1: boolean = false;
  sidebarVisible2: boolean = false;
  sidebarVisible3: boolean = false;
  sidebarVisible4: boolean = false;

  ngOnInit(): void {
    this.operationFormRequest = this.fb.group({
      account:[{value:'', disabled: true}],
      type: ['', Validators.required],
      mount: ['', Validators.required],
    });
    this.overdraftForm = this.fb.group({
      account:[{value:'', disabled: true}],
      overdraft: [, Validators.required]
    });

    this.iRateForm = this.fb.group({
      account:[{value:'', disabled: true}],
      interestRate: ['', Validators.required]
    });

    this.transferFormRequest = this.fb.group({
      origin:[{value:'', disabled:true}],
      destination:['', Validators.required],
      mount:['', Validators.required]
    })
  }

  selectedAccount!: BankAccount;
  loadAccount(account: BankAccount) {
    this.selectedAccount = account;
    console.log(this.selectedAccount);
  }

  onCreateNewOperation() {
    this.confirmService.confirm({
      acceptLabel: acceptLabel,
      rejectLabel: rejectLabel,
      message: confirmMsg,
      accept: () => {
        let operation: Operation = this.operationFormRequest.value
        operation.accountId = this.selectedAccount.accountId;
        operation.type = this.operationFormRequest.get('type')?.value.type;
        this.operationService.create(operation).subscribe({
          next: (value: Operation) => {
            console.log(operation);
            this.messageService.add({
              key: 'key1',
              severity: severity1,
              detail: detailMsg1,
              sticky: true
            });
            this.bankAccountEventService.publishEvent(BankAccountEvent.REFRESH);
            return value;
          },
          error: (err) => {
            alert(`error of creating operation ${err}`);
          },
          complete: () => {
            console.log('observable completed');
          }
        });
      },
      reject: () => {
        this.messageService.add({
          key: 'key2',
          severity: severity2,
          detail: detailMsg2,
          sticky: true
        });
        return null;
      }
    });
  }

  onSwitchStateAccount(account: BankAccount) {
    let accountSwitchStateDto = new AccountSwitchStateDto();
    accountSwitchStateDto.accountId = account.accountId;

    this.confirmService.confirm({
      acceptLabel: acceptLabel,
      rejectLabel: rejectLabel,
      message: confirmMsg,
      accept: () => {
        if (account.state == 'active') {
          accountSwitchStateDto.state = 'suspended';
          this.bankAccountService.switchState(accountSwitchStateDto).subscribe({
            next: (value: BankAccount) => {
              this.messageService.add({
                key: "key1",
                severity: severity1,
                detail: detailMsg1,
                sticky: true
              });
              this.bankAccountEventService.publishEvent(BankAccountEvent.REFRESH);
              return value;
            }
          });
        }
        else if (account.state == "suspended") {
          accountSwitchStateDto.state = 'active';
          this.bankAccountService.switchState(accountSwitchStateDto).subscribe({
            next: (value: BankAccount) => {
              this.messageService.add({
                key: "key1",
                severity: severity1,
                detail: detailMsg1,
                sticky: true
              });
              this.bankAccountEventService.publishEvent(BankAccountEvent.REFRESH);
              return value;
            },
            error: (err) => {
              alert('error accured');
            },
            complete: () => {
              console.log('observable completed');
            }
          });
        }
      },
      reject: () => {

        this.messageService.add({
          key: "key2",
          severity: severity2,
          detail: detailMsg2,
          sticky: true
        });

      }
    });
  }

  changeAccountOverdraft() {
    let accountOverdraftDto = new BankAccountOverdraftDto();
    accountOverdraftDto.accountId = this.selectedAccount.accountId;
    accountOverdraftDto.overdraft = this.overdraftForm.value.overdraft;
    console.log(this.selectedAccount);

    this.confirmService.confirm({
      acceptLabel: acceptLabel,
      rejectLabel: rejectLabel,
      message: confirmMsg,
      accept: () => {
        this.bankAccountService.changeOverdraft(accountOverdraftDto).subscribe({
          next: (value: BankAccount) => {
            this.messageService.add({
              key: "key1",
              severity: severity1,
              detail: detailMsg1,
              sticky: true
            });
            this.bankAccountEventService.publishEvent(BankAccountEvent.REFRESH);
            return value;
          },
          error: (err) => {
            alert('error accured');
          },
          complete: () => {
            console.log('observable completed');
          }
        });
      },
      reject: () => {
        this.messageService.add({
          key: "key2",
          severity: severity2,
          detail: detailMsg2,
          sticky: true
        });
        return null;
      }
    });
  }

  changeAccountInterestRate() {
    let iRateDto = new BankAccountInterestRateDto();
    iRateDto.accountId = this.selectedAccount.accountId;
    iRateDto.interestRate = this.iRateForm.value.interestRate;
    console.log(this.selectedAccount);
    this.confirmService.confirm({
      acceptLabel: acceptLabel,
      rejectLabel: rejectLabel,
      message: confirmMsg,
      accept: () => {
        this.bankAccountService.changeInterestate(iRateDto).subscribe({
          next: (value: BankAccount) => {
            this.messageService.add({
              key: "key1",
              severity: severity1,
              detail: detailMsg1,
              sticky: true
            });
            this.bankAccountEventService.publishEvent(BankAccountEvent.REFRESH);
            return value;
          }
        });
      },
      reject: () => {
        this.messageService.add({
          key: "key2",
          severity: severity2,
          detail: detailMsg2,
          sticky: true
        });
        return null;
      }
    });
  }

  onTransferOperation(){
    let transferDto : TransferDto = this.transferFormRequest.getRawValue();
    this.confirmService.confirm({
      acceptLabel: acceptLabel,
      rejectLabel: rejectLabel,
      message: confirmMsg,
      accept: () => {
        this.operationService.createTransfer(transferDto).subscribe({
          next: (value: Map<string,BankAccount>) => {
            this.messageService.add({
              key: "key1",
              severity: severity1,
              detail: detailMsg1,
              sticky: true
            });
            console.log(value);
            this.bankAccountEventService.publishEvent(BankAccountEvent.REFRESH);
            return value;
          }
        });
      },
      reject: () => {
        this.messageService.add({
          key: "key2",
          severity: severity2,
          detail: detailMsg2,
          sticky: true
        });
        return null;
      }
    });
  }

}
