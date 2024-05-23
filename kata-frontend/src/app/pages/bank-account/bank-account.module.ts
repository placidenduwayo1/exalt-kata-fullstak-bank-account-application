import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BankAccountRoutingModule } from './bank-account-routing.module';
import { BankAccountManagerComponent } from './bank-account-manager/bank-account-manager.component';
import { AccountsListComponent } from './bank-account-manager/accounts-list/accounts-list.component';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
import { ButtonModule } from 'primeng/button';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessagesModule } from 'primeng/messages';
import { TooltipModule } from 'primeng/tooltip';
import { SidebarModule } from 'primeng/sidebar';



@NgModule({
  declarations: [
    BankAccountManagerComponent,
    AccountsListComponent
  ],
  imports: [
    CommonModule,
    BankAccountRoutingModule,
    TableModule,
    TagModule,
    CardModule,
    InputTextModule,
    FormsModule,
    ReactiveFormsModule,
    DropdownModule,
    ButtonModule,
    ConfirmDialogModule,
    MessagesModule,
    TooltipModule,
    SidebarModule
  ],
  providers:[ConfirmationService, MessageService]
})
export class BankAccountModule { }
