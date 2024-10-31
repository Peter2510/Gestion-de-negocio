import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SinAutorizacionComponent } from './sin-autorizacion.component';

describe('SinAutorizacionComponent', () => {
  let component: SinAutorizacionComponent;
  let fixture: ComponentFixture<SinAutorizacionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SinAutorizacionComponent]
    });
    fixture = TestBed.createComponent(SinAutorizacionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
