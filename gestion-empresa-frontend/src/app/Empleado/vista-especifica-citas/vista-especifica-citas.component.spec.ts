import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VistaEspecificaCitasComponent } from './vista-especifica-citas.component';

describe('VistaEspecificaCitasComponent', () => {
  let component: VistaEspecificaCitasComponent;
  let fixture: ComponentFixture<VistaEspecificaCitasComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VistaEspecificaCitasComponent]
    });
    fixture = TestBed.createComponent(VistaEspecificaCitasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
