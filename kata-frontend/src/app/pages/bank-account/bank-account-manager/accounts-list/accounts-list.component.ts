import { Component, Input, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { acceptLabel, confirmMsg, rejectLabel, severity1 } from 'src/app/pages/confirmation.message';
import { AccountSwitchStateDto } from 'src/app/shared/models/bank-account/account-swithstate-dto.model';
import { BankAccount } from 'src/app/shared/models/bank-account/bank-account.model';
import { BankAccountService } from 'src/app/shared/services/bank-account.service';

@Component({
  selector: 'app-accounts-list',
  templateUrl: './accounts-list.component.html',
  styleUrls: ['./accounts-list.component.css']
})
export class AccountsListComponent {

  @Input() accountsList: BankAccount[] = [];
  router = inject(Router);
  bankAccountService = inject(BankAccountService);
  confirmService = inject(ConfirmationService);
  messageService = inject(MessageService);

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

  onCreateOperation(account: BankAccount) {
    this.router.navigateByUrl(`session/operations/create-operation/${account.accountId}`);
  }

  onSwitchStateAccount(account: BankAccount) {
    let accountSwitchStateDto = new AccountSwitchStateDto();
    accountSwitchStateDto.accountId = account.accountId;

    this.confirmService.confirm({
      acceptLabel: acceptLabel,
      rejectLabel: rejectLabel,
      message: "Confirm operation",
      accept: () => {
        if (account.state == 'active') {
          accountSwitchStateDto.state = 'suspended';
          this.bankAccountService.switchState(accountSwitchStateDto).subscribe({
            next: (value: BankAccount) => {
              this.messageService.add({
                key: "key1",
                severity: severity1,
                detail: "Bank Account suspened",
                sticky: true
              });
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
                detail: "Bank Account activated",
                sticky: true
              });
              return value;
            }
          });
        }
      },
      reject: () => {

        this.messageService.add({
          key: "key2",
          severity: severity1,
          detail: "Operation rejected",
          sticky: true
        });

      }
    });
  }
}
