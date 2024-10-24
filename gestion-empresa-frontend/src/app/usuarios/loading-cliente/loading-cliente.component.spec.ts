import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadingClienteComponent } from './loading-cliente.component';

describe('LoadingClienteComponent', () => {
  let component: LoadingClienteComponent;
  let fixture: ComponentFixture<LoadingClienteComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LoadingClienteComponent]
    });
    fixture = TestBed.createComponent(LoadingClienteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
