import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BankAccount } from 'src/app/shared/models/bank-account/bank-account.model';

@Component({
  selector: 'app-bank-account-manager',
  templateUrl: './bank-account-manager.component.html',
  styleUrls: ['./bank-account-manager.component.css']
})
export class BankAccountManagerComponent implements OnInit{
  accounts!: BankAccount[];
  activatedRoute = inject(ActivatedRoute);

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
    })
  }

}
