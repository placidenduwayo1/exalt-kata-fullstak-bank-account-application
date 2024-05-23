import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Customer } from 'src/app/shared/models/customer/customer.model';
import { CustomerEvent } from 'src/app/shared/models/event.model';
import { CustomerService } from 'src/app/shared/services/customer.service';
import { CustomerEventService } from 'src/app/shared/services/event.service.publisher';

@Component({
  selector: 'app-customer-manager',
  templateUrl: './customer-manager.component.html',
  styleUrls: ['./customer-manager.component.css']
})
export class CustomerManagerComponent implements OnInit {

  activatedRoute = inject(ActivatedRoute);
  customers: Customer[] = [];
  customerEventService = inject(CustomerEventService);
  customerService = inject(CustomerService);

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

    this.customerEventService.customerEventObservable.subscribe({
      next: (event)=>{
        if(event==CustomerEvent.REFRESH){
          this.customerService.getAll().subscribe({
            next: (data: Customer[])=>{
              this.customers = data;
            },
            error: (err)=>{
              alert(`an error occured ${err}`);
            },
            complete: ()=>{
              console.log('obserable completed');
            }
          });
        }
      }
    });
  }

}
