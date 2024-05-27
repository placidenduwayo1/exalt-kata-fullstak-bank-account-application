import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OperationManagerComponent } from './operation-manager/operation-manager.component';
import { getAllOperationsResolve, getAllTransferResolve } from 'src/app/shared/services/route.resolve';
import { TransferListComponent } from './operation-manager/transfer-list/transfer-list.component';

const routes: Routes = [
  {
    path: '', component: OperationManagerComponent,
    resolve: {
      allOperations: getAllOperationsResolve
    }
  },
  {
    path: 'transfers', component: TransferListComponent, resolve:{
      allTransfers: getAllTransferResolve
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OperationRoutingModule { }
