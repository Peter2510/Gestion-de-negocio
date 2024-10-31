import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerEmpleadosComponent } from './ver-empleados.component';

describe('VerEmpleadosComponent', () => {
  let component: VerEmpleadosComponent;
  let fixture: ComponentFixture<VerEmpleadosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VerEmpleadosComponent]
    });
    fixture = TestBed.createComponent(VerEmpleadosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
