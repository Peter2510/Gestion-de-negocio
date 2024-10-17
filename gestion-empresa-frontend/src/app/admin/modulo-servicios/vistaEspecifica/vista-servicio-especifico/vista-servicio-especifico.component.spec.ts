import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VistaServicioEspecificoComponent } from './vista-servicio-especifico.component';

describe('VistaServicioEspecificoComponent', () => {
  let component: VistaServicioEspecificoComponent;
  let fixture: ComponentFixture<VistaServicioEspecificoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VistaServicioEspecificoComponent]
    });
    fixture = TestBed.createComponent(VistaServicioEspecificoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
