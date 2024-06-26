import { Component, Input, inject } from '@angular/core';
import { Operation } from 'src/app/shared/models/operation/operation.model';

@Component({
  selector: 'app-operations-list',
  templateUrl: './operations-list.component.html',
  styleUrls: ['./operations-list.component.css']
})
export class OperationsListComponent {

  @Input () opartionsList!: Operation[];

  getOperationTypeSeverity(type: string): string {
    if(type=='depot'){
      return 'success'
    }
    else if(type=='retrait'){
      return 'warning'
    }
    else {
      return 'danger'
    }
  }

}
