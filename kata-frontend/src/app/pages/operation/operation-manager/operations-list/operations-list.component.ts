import { Component, Input } from '@angular/core';
import { retry } from 'rxjs';
import { Operation } from 'src/app/shared/models/operation.model';

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
      return 'danger'
    }
    else {
      return 'danger'
    }
  }

}
