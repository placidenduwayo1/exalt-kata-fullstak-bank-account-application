import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-transfer-list',
  templateUrl: './transfer-list.component.html',
  styleUrls: ['./transfer-list.component.css']
})
export class TransferListComponent implements OnInit {
  activatedRoute = inject(ActivatedRoute);
  transfers!: any[];

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ allTransfers }) => {
      this.transfers = allTransfers;
      console.log(this.transfers);
    })
  }

  getAccountTypeSeverity(type: string): string {
    if (type == 'saving') {
      return 'success'
    }
    else if(type=='current'){
      return 'primary'
    }
    else return 'danger'
  }

}
