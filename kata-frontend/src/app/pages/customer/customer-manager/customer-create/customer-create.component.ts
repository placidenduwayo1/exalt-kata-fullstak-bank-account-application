import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ConfirmationService, MessageService } from 'primeng/api';
import { acceptLabel, confirmMsg, detailMsg1, detailMsg2, rejectLabel, severity1, severity2 } from 'src/app/pages/enum';
import { Customer } from 'src/app/shared/models/customer/customer.model';
import { Request } from 'src/app/shared/models/customer/request.model';
import { CustomerEvent } from 'src/app/shared/models/event.model';
import { CustomerService } from 'src/app/shared/services/customer.service';
import { CustomerEventService } from 'src/app/shared/services/event.service.publisher';

@Component({
  selector: 'app-customer-create',
  templateUrl: './customer-create.component.html',
  styleUrls: ['./customer-create.component.css']
})
export class CustomerCreateComponent implements OnInit {
  customerRequest!: FormGroup;
  fb = inject(FormBuilder);
  private minLen: number = 2;
  private min: number = 1;
  customerEventService = inject(CustomerEventService);
  customerService = inject(CustomerService);
  confirmSevice = inject(ConfirmationService)
  messageService = inject(MessageService);

  ngOnInit(): void {
    //build customer form
    this.customerRequest = this.fb.group({
      customer: this.fb.group({
        firstname: ['', [Validators.required, Validators.minLength(this.minLen)]],
        lastname: ['', [Validators.required, Validators.minLength(this.minLen)]],
        email: ['', [Validators.required, Validators.minLength(4)]],
      }),
      address: this.fb.group({
        streetNum: ['', [Validators.required, Validators.min(this.min)]],
        streetName: ['', [Validators.required, Validators.minLength(this.minLen)]],
        poBox: ['', [Validators.required, Validators.min(this.min)]],
        city: ['', [Validators.required, Validators.minLength(this.minLen)]],
        country: ['', [Validators.required, Validators.minLength(this.minLen)]]
      })
    });

    // create customer with form data
    this.customerEventService.customerEventObservable.subscribe((event: CustomerEvent) => {
      if (event == CustomerEvent.CUSTOMER_CREATE) {
        console.log(event);
        let request: Request = new Request();
        request.customerDto = this.customerRequest.value.customer;
        request.addressDto = this.customerRequest.value.address;
        console.log(request)
        this.confirmSevice.confirm({
          acceptLabel: acceptLabel,
          rejectLabel: rejectLabel,
          message: confirmMsg,
          accept: () => {
            this.customerService.create(request).subscribe({
              next: (data: Customer) => {
                console.log(data);
                this.messageService.add({
                  key: "key1",
                  severity: severity1,
                  detail: detailMsg1,
                  sticky: true
                });
              }
            });
          },
          reject: () => {
            this.messageService.add({
              key: "key2",
              severity: severity2,
              detail: detailMsg2,
              sticky: true
            });
          }
        });
      }
    });
  }

  createCustomer() {
    this.customerEventService.publishEvent(CustomerEvent.CUSTOMER_CREATE);
  }
}
