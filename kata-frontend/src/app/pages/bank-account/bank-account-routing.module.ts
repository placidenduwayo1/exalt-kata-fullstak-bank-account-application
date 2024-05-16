import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BankAccountManagerComponent } from './bank-account-manager/bank-account-manager.component';
import { getAllBankAccountsResolve } from 'src/app/shared/services/route.resolve';

const routes: Routes = [
  {
    path: '', component: BankAccountManagerComponent,
    resolve: {
      allAccounts: getAllBankAccountsResolve
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BankAccountRoutingModule { }
