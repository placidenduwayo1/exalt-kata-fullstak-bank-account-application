import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomerRoutingModule } from './customer-routing.module';
import { CustomerManagerComponent } from './customer-manager/customer-manager.component';
import { CustomersListComponent } from './customer-manager/customers-list/customers-list.component';
import { TableModule } from 'primeng/table';
import { SelectButtonModule } from 'primeng/selectbutton';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CardModule } from 'primeng/card';
import { CustomerCreateComponent } from './customer-manager/customer-create/customer-create.component';
import { InputTextModule } from 'primeng/inputtext';
import { PanelModule } from 'primeng/panel';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';



@NgModule({
  declarations: [
    CustomerManagerComponent,
    CustomersListComponent,
    CustomerCreateComponent
  ],
  imports: [
    CommonModule,
    CustomerRoutingModule,
    TableModule,
    SelectButtonModule,
    FormsModule,
    ReactiveFormsModule,
    CardModule,
    InputTextModule,
    PanelModule,
    ButtonModule,
    TagModule
  ]
})
export class CustomerModule { }
