import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalCitaEspecificaComponent } from './modal-cita-especifica.component';

describe('ModalCitaEspecificaComponent', () => {
  let component: ModalCitaEspecificaComponent;
  let fixture: ComponentFixture<ModalCitaEspecificaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalCitaEspecificaComponent]
    });
    fixture = TestBed.createComponent(ModalCitaEspecificaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
