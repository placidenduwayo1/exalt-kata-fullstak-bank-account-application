import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BankAccountManagerComponent } from './bank-account-manager.component';

describe('BankAccountManagerComponent', () => {
  let component: BankAccountManagerComponent;
  let fixture: ComponentFixture<BankAccountManagerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BankAccountManagerComponent]
    });
    fixture = TestBed.createComponent(BankAccountManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
