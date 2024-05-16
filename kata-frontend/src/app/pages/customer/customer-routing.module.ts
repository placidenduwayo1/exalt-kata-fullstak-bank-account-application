import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CustomerManagerComponent } from './customer-manager/customer-manager.component';
import { CustomerCreateComponent } from './customer-manager/customer-create/customer-create.component';
import { getAllCustomersResolve } from 'src/app/shared/services/route.resolve';

const routes: Routes = [
  {
    path:'',component: CustomerManagerComponent,
    resolve:{
      allCustomers: getAllCustomersResolve
    }
  },
  {
    path:'create', component: CustomerCreateComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule { }
