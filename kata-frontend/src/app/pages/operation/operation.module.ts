import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OperationRoutingModule } from './operation-routing.module';
import { OperationManagerComponent } from './operation-manager/operation-manager.component';
import { OperationsListComponent } from './operation-manager/operations-list/operations-list.component';
import { TableModule } from 'primeng/table';
import { CardModule } from 'primeng/card';
import { TagModule } from 'primeng/tag';
import { OperationCreateComponent } from './operation-manager/operation-create/operation-create.component';
import { MessagesModule } from 'primeng/messages';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Dropdown, DropdownModule } from 'primeng/dropdown';
import { ConfirmationService, MessageService } from 'primeng/api';
import { InputTextModule } from 'primeng/inputtext';


@NgModule({
  declarations: [
    OperationManagerComponent,
    OperationsListComponent,
    OperationCreateComponent
  ],
  imports: [
    CommonModule,
    OperationRoutingModule,
    TableModule,
    CardModule,
    TagModule,
    MessagesModule,
    ConfirmDialogModule,
    ReactiveFormsModule,
    DropdownModule,
    FormsModule,
    InputTextModule
  ],
  providers:[ConfirmationService, MessageService]
})
export class OperationModule { }
