import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalCreacionCitaComponent } from './modal-creacion-cita.component';

describe('ModalCreacionCitaComponent', () => {
  let component: ModalCreacionCitaComponent;
  let fixture: ComponentFixture<ModalCreacionCitaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalCreacionCitaComponent]
    });
    fixture = TestBed.createComponent(ModalCreacionCitaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
