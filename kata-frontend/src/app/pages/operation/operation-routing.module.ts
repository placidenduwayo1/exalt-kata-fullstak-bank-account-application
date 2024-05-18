import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OperationManagerComponent } from './operation-manager/operation-manager.component';
import { getAllOperationsResolve } from 'src/app/shared/services/route.resolve';
import { OperationCreateComponent } from './operation-manager/operation-create/operation-create.component';

const routes: Routes = [
  {
    path: '', component: OperationManagerComponent,
    resolve: {
      allOperations: getAllOperationsResolve
    }
  },
  {
    path: 'create-operation/:accountId', component: OperationCreateComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OperationRoutingModule { }
