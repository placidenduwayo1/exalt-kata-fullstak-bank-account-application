import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { acceptLabel, rejectLabel, confirmMsg, severity1, detailMsg1, severity2, detailMsg2 } from 'src/app/pages/confirmation.message';
import { BankAccount } from 'src/app/shared/models/bank-account/bank-account.model';
import { Customer } from 'src/app/shared/models/customer/customer.model';
import { BankAccountEvent } from 'src/app/shared/models/event.model';
import { BankAccountService } from 'src/app/shared/services/bank-account.service';
import { CustomerService } from 'src/app/shared/services/customer.service';
import { BankAccountEventService } from 'src/app/shared/services/event.service.publisher';

interface BankAccountType {
  type: string;
}

@Component({
  selector: 'app-account-create',
  templateUrl: './account-create.component.html',
  styleUrls: ['./account-create.component.css']
})
export class AccountCreateComponent implements OnInit {
  fb = inject(FormBuilder);
  accountFormRequest!: FormGroup;
  customers!: Customer[];
  selectedCustomer!: Customer;
  customerService = inject(CustomerService);
  confirmSevice = inject(ConfirmationService)
  messageService = inject(MessageService);
  bankAccountService = inject(BankAccountService);
  bankAccountEventService = inject(BankAccountEventService);
  route = inject(ActivatedRoute);

  customerId!: string;
  customer!: Customer;

  accountTypes:BankAccountType[] = [{type:'current'}, {type:'saving'}];
  selectedType!: BankAccountType;

  ngOnInit(): void {
    this.customerId = this.route.snapshot.params['customerId']
    this.customerService.getCustomer(this.customerId).subscribe({
      next: (value: Customer) => {
        this.customer = value;
        console.log(this.customer)
      },
      error: (err) => {
        alert(`error of loading customer ${err}`);
      },
      complete: () => {
        console.log('observable complete');
      }
    });

    this.accountFormRequest = this.fb.group({
      type: ['', Validators.required],
      balance: ['', Validators.required],
      customer: [{value:this.customerId, disabled:true}]
    });

    // create a new bank account from form data
    this.bankAccountEventService.bankAccountEventObservable.subscribe({
      next: (event: BankAccountEvent) => {
        if (event == BankAccountEvent.BANK_ACCOUNT_CREATE) {
          let bankAccount: BankAccount = this.accountFormRequest.value;
          bankAccount.customerId = this.customerId;
          bankAccount.type = this.accountFormRequest.get('type')?.value.type;
          this.confirmSevice.confirm({
            acceptLabel: acceptLabel,
            rejectLabel: rejectLabel,
            message: confirmMsg,

            accept: () => {
              this.bankAccountService.create(bankAccount).subscribe({
                next: (data: BankAccount) => {
                  console.log(data);
                  this.messageService.add({
                    key: "key1",
                    severity: severity1,
                    detail: detailMsg1,
                    sticky: true
                  });
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
            }
          });
        }
      },
      error(err) {
        alert('error of laoding bank account events');
      },
      complete() {
        console.log('observable completed');
      },
    });
  }

  createAccount() {
    this.bankAccountEventService.publishEvent(BankAccountEvent.BANK_ACCOUNT_CREATE);
  }
}
