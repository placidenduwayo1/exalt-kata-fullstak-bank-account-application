import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BankAccount } from 'src/app/shared/models/bank-account/bank-account.model';
import { BankAccountEvent } from 'src/app/shared/models/event.model';
import { BankAccountService } from 'src/app/shared/services/bank-account.service';
import { BankAccountEventService } from 'src/app/shared/services/event.service.publisher';

@Component({
  selector: 'app-bank-account-manager',
  templateUrl: './bank-account-manager.component.html',
  styleUrls: ['./bank-account-manager.component.css']
})
export class BankAccountManagerComponent implements OnInit{
  accounts!: BankAccount[];
  activatedRoute = inject(ActivatedRoute);
  bankAccountEventService = inject(BankAccountEventService);
  bankAccountService = inject(BankAccountService);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe({
      next:({allAccounts})=>{
        this.accounts = allAccounts;
        console.log(this.accounts);
      },
      error(err) {
        alert(`error has occured ${err}`)
      },
      complete:()=>{
        console.log('observable completed')
      }
    });

    this.bankAccountEventService.bankAccountEventObservable.subscribe((event:BankAccountEvent)=>{
      if(event==BankAccountEvent.REFRESH){
        this.bankAccountService.getAll().subscribe({
          next:(value: BankAccount[])=>{
            this.accounts = value;
          }
        })
      }
    });
  }

}
