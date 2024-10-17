import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreacionServicioComponent } from './creacion-servicio.component';

describe('CreacionServicioComponent', () => {
  let component: CreacionServicioComponent;
  let fixture: ComponentFixture<CreacionServicioComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreacionServicioComponent]
    });
    fixture = TestBed.createComponent(CreacionServicioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
