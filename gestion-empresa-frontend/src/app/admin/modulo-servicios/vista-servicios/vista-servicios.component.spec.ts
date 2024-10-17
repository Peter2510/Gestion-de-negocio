import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VistaServiciosComponent } from './vista-servicios.component';

describe('VistaServiciosComponent', () => {
  let component: VistaServiciosComponent;
  let fixture: ComponentFixture<VistaServiciosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VistaServiciosComponent]
    });
    fixture = TestBed.createComponent(VistaServiciosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
