import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SessionComponent } from './pages/session/session.component';

const routes: Routes = [
  {
    path: 'login', component: LoginComponent
  },
  {
    path: '', redirectTo: '/login', pathMatch: 'full'
  },
  {
    path: 'session', component: SessionComponent, children: [
      {
        path: 'accueil', loadChildren: () => import('./pages/accueil/accueil.module')
          .then(m => m.AccueilModule)
      }
      ,
      {
        path: 'customers', loadChildren: () => import('./pages/customer/customer.module')
          .then(m => m.CustomerModule)
      }
      ,
      {
        path: 'accounts', loadChildren: () => import('./pages/bank-account/bank-account.module')
          .then(m => m.BankAccountModule)
      },
      {
        path: 'operations', loadChildren: () => import('./pages/operation/operation.module')
          .then(m => m.OperationModule)
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
