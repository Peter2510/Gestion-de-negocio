import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EdicionPerfilComponent } from './edicion-perfil.component';

describe('EdicionPerfilComponent', () => {
  let component: EdicionPerfilComponent;
  let fixture: ComponentFixture<EdicionPerfilComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EdicionPerfilComponent]
    });
    fixture = TestBed.createComponent(EdicionPerfilComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
