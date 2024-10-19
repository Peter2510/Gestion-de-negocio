import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerCategoriasComponent } from './ver-categorias.component';

describe('VerCategoriasComponent', () => {
  let component: VerCategoriasComponent;
  let fixture: ComponentFixture<VerCategoriasComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VerCategoriasComponent]
    });
    fixture = TestBed.createComponent(VerCategoriasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
