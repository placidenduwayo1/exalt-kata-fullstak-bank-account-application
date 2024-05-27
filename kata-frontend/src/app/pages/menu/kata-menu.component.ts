import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-kata-menu',
  templateUrl: './kata-menu.component.html',
  styleUrls: ['./kata-menu.component.css']
})
export class KataMenuComponent {

  items!: MenuItem[];
  activeItem!: MenuItem;

  private router = inject(Router);


  ngOnInit(): void {
    this.items = [
      {
        label: 'Home', icon: 'pi pi-fw pi-home',
        command: () => this.router.navigateByUrl('session/accueil')
      },
      {
        label: 'api-customer',
        items: [
          {
            label: 'customers', icon: 'pi pi-list',
            command: () => this.router.navigateByUrl("/session/customers")
          },
          {
            label: 'create', icon: 'pi pi-plus-circle',

            command: ()=>this.router.navigateByUrl('session/customers/create')
          }
        ]
      },
      {
        label: 'api-bank-account',
        items: [
          {
            label: 'bank-accounts', icon: 'pi pi-list',
            command: ()=> this.router.navigateByUrl("session/accounts")
          }
        ]
      },
      {
        label: 'api-operations',
        items: [
          {
            label: 'depot/retrait', icon: 'pi pi-list',
            command:()=>this.router.navigateByUrl('session/operations')
          },
          {
            label: 'transfert', icon: 'pi pi-list',
            command:()=>this.router.navigateByUrl('session/operations/transfers')
          }
        ]
      },
      {
        label:'', icon:'pi pi-power-off',
        command:()=>this.router.navigateByUrl('login')
      }
    ];

    this.activeItem = this.items[0];
  }
}
