import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Operation } from 'src/app/shared/models/operation/operation.model';

@Component({
  selector: 'app-operation-manager',
  templateUrl: './operation-manager.component.html',
  styleUrls: ['./operation-manager.component.css']
})
export class OperationManagerComponent implements OnInit{

  operations!: Operation[];
  activatedRoute = inject(ActivatedRoute);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe({
      next:({allOperations})=>{
        this.operations = allOperations;
        console.log(this.operations)
      }
    });
  }
}
