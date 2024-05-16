import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KataMenuComponent } from './kata-menu.component';

describe('KataMenuComponent', () => {
  let component: KataMenuComponent;
  let fixture: ComponentFixture<KataMenuComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [KataMenuComponent]
    });
    fixture = TestBed.createComponent(KataMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
