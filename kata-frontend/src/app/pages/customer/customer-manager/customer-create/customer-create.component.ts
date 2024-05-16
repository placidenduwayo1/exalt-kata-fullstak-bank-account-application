import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Address } from 'src/app/shared/models/customer/address.model';
import { Customer } from 'src/app/shared/models/customer/customer.model';
import { Request } from 'src/app/shared/models/customer/request.model';
import { CustomerEvent } from 'src/app/shared/models/events/event.model';
import { CustomerService } from 'src/app/shared/services/customer.service';
import { CustomerEventService } from 'src/app/shared/services/event.service.publisher';

@Component({
  selector: 'app-customer-create',
  templateUrl: './customer-create.component.html',
  styleUrls: ['./customer-create.component.css']
})
export class CustomerCreateComponent implements OnInit{
  customerFg!: FormGroup;
  fb = inject(FormBuilder);
  private minLen: number = 2;
  private min: number = 1;
  customerEventService = inject(CustomerEventService);
  customerService = inject(CustomerService);

  ngOnInit(): void {
    //build customer form
    this.customerFg = this.fb.group({
      firstname:['',[Validators.required, Validators.minLength(this.minLen)]],
      lastname:['',[Validators.required, Validators.minLength(this.minLen)]],
      email:['',[Validators.required, Validators.minLength(4)]],
      address: this.fb.group({
        streetNum:['', [Validators.required, Validators.min(this.min)]],
        streetName:['', [Validators.required, Validators.minLength(this.minLen)]],
        poBox:['', [Validators.required, Validators.min(this.min)]],
        city:['', [Validators.required, Validators.minLength(this.minLen)]],
        country:['', [Validators.required, Validators.minLength(this.minLen)]]
      })
    });

    // create customer with form data
    this.customerEventService.customerEventObservable.subscribe((event:CustomerEvent)=>{
      if(event==CustomerEvent.CUSTOMER_CREATE){
        console.log(event);
        let customer: Customer = new Customer();
        customer.firstname = this.customerFg.value.firstname;
        customer.lastname = this.customerFg.value.lastname;
        customer.email = this.customerFg.value.email;

        let address: Address = new Address();
        address.streetNum = this.customerFg.value.address.streetNum;
        address.streetName = this.customerFg.value.address.streetName;
        address.poBox = this.customerFg.value.address.poBox;
        address.city = this.customerFg.value.address.city;
        address.country = this.customerFg.value.address.country;
        let request: Request= new Request();
        request.customer = customer;
        request.address = address;
        console.log(request)
       
        this.customerService.create(request).subscribe(data=>{
          console.log(data);
        });
      }
    });
  }

  createCustomer(){
    this.customerEventService.publishEvent(CustomerEvent.CUSTOMER_CREATE);
  }
}
