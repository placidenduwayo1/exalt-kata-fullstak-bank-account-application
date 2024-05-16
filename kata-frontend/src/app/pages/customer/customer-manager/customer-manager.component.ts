import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Customer } from 'src/app/shared/models/customer/customer.model';
import { CustomerService } from 'src/app/shared/services/customer.service';

@Component({
  selector: 'app-customer-manager',
  templateUrl: './customer-manager.component.html',
  styleUrls: ['./customer-manager.component.css']
})
export class CustomerManagerComponent implements OnInit {

  activatedRoute = inject(ActivatedRoute);
  customers: Customer[] = [];

  ngOnInit(): void {
    this.activatedRoute.data.subscribe({
      next: ({ allCustomers }) => {
        this.customers = allCustomers;
        console.log(this.customers);
      },
      error(err) {
        alert(`error has occured ${err}`)
      },
      complete: () => {
        console.log('observable completed')
      }
    });
  }

}
