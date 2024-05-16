import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OperationRoutingModule } from './operation-routing.module';
import { OperationManagerComponent } from './operation-manager/operation-manager.component';
import { OperationsListComponent } from './operation-manager/operations-list/operations-list.component';
import { TableModule } from 'primeng/table';
import { CardModule } from 'primeng/card';
import { TagModule } from 'primeng/tag';


@NgModule({
  declarations: [
    OperationManagerComponent,
    OperationsListComponent
  ],
  imports: [
    CommonModule,
    OperationRoutingModule,
    TableModule,
    CardModule,
    TagModule
  ]
})
export class OperationModule { }
