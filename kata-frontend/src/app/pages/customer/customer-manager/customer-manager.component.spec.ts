import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerManagerComponent } from './customer-manager.component';

describe('CustomerManagerComponent', () => {
  let component: CustomerManagerComponent;
  let fixture: ComponentFixture<CustomerManagerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CustomerManagerComponent]
    });
    fixture = TestBed.createComponent(CustomerManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
