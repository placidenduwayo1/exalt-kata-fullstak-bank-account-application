import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BankAccountRoutingModule } from './bank-account-routing.module';
import { BankAccountManagerComponent } from './bank-account-manager/bank-account-manager.component';
import { AccountsListComponent } from './bank-account-manager/accounts-list/accounts-list.component';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';


@NgModule({
  declarations: [
    BankAccountManagerComponent,
    AccountsListComponent
  ],
  imports: [
    CommonModule,
    BankAccountRoutingModule,
    TableModule,
    TagModule
  ]
})
export class BankAccountModule { }
