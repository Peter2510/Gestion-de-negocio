import { TestBed } from '@angular/core/testing';

import { JornadasLaboralesServicioService } from './jornadas-laborales-servicio.service';

describe('JornadasLaboralesServicioService', () => {
  let service: JornadasLaboralesServicioService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JornadasLaboralesServicioService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
