import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RolEspecificoComponent } from './rol-especifico.component';

describe('RolEspecificoComponent', () => {
  let component: RolEspecificoComponent;
  let fixture: ComponentFixture<RolEspecificoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RolEspecificoComponent]
    });
    fixture = TestBed.createComponent(RolEspecificoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
