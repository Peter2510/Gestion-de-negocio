import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FacturasComprobantesComponent } from './facturas-comprobantes.component';

describe('FacturasComprobantesComponent', () => {
  let component: FacturasComprobantesComponent;
  let fixture: ComponentFixture<FacturasComprobantesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FacturasComprobantesComponent]
    });
    fixture = TestBed.createComponent(FacturasComprobantesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
