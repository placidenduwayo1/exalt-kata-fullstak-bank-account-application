import { Component, Input, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Customer } from 'src/app/shared/models/customer/customer.model';

@Component({
  selector: 'app-customers-list',
  templateUrl: './customers-list.component.html',
  styleUrls: ['./customers-list.component.css']
})
export class CustomersListComponent {
  @Input() customers!: Customer[];
  router = inject(Router);

  getCustomerStateServerity(state: string): string {
    if(state=='archive'){
      return 'danger'
    }
    else if(state=='active'){
      return 'success'
    }
    else {
      return 'danger'
    }
  }

  onCreateCustomerAccount(customer: Customer){
    this.router.navigateByUrl(`session/accounts/create-account/${customer.customerId}`)
  }
}
