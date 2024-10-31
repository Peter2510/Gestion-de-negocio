import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderEmpleadoComponent } from './header-empleado.component';

describe('HeaderEmpleadoComponent', () => {
  let component: HeaderEmpleadoComponent;
  let fixture: ComponentFixture<HeaderEmpleadoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HeaderEmpleadoComponent]
    });
    fixture = TestBed.createComponent(HeaderEmpleadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
