import { Component, Input, OnInit } from '@angular/core';
import { Customer } from 'src/app/shared/models/customer/customer.model';

@Component({
  selector: 'app-customers-list',
  templateUrl: './customers-list.component.html',
  styleUrls: ['./customers-list.component.css']
})
export class CustomersListComponent {
  @Input() customers!: Customer[]

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
}
