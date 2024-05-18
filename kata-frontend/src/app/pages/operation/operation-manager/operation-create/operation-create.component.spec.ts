import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperationCreateComponent } from './operation-create.component';

describe('OperationCreateComponent', () => {
  let component: OperationCreateComponent;
  let fixture: ComponentFixture<OperationCreateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OperationCreateComponent]
    });
    fixture = TestBed.createComponent(OperationCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
