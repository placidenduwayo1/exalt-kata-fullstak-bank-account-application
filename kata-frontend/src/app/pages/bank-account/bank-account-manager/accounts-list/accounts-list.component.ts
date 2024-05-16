import { Component, Input, OnInit } from '@angular/core';
import { BankAccount } from 'src/app/shared/models/bank-account/bank-account.model';

@Component({
  selector: 'app-accounts-list',
  templateUrl: './accounts-list.component.html',
  styleUrls: ['./accounts-list.component.css']
})
export class AccountsListComponent {

  @Input() accountsList: BankAccount[] = [];

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
        return 'warning'
      case 'current':
        return 'primary'
      default:
        return 'warning'
    }
  }
}
