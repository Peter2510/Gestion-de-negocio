import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VistaEmpleadoComponent } from './vista-empleado.component';

describe('VistaEmpleadoComponent', () => {
  let component: VistaEmpleadoComponent;
  let fixture: ComponentFixture<VistaEmpleadoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VistaEmpleadoComponent]
    });
    fixture = TestBed.createComponent(VistaEmpleadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
