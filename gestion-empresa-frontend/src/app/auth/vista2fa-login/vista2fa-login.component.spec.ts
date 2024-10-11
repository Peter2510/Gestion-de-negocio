import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Vista2faLoginComponent } from './vista2fa-login.component';

describe('Vista2faLoginComponent', () => {
  let component: Vista2faLoginComponent;
  let fixture: ComponentFixture<Vista2faLoginComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [Vista2faLoginComponent]
    });
    fixture = TestBed.createComponent(Vista2faLoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
