import { TestBed } from '@angular/core/testing';

import { CitasServicioService } from './citas-servicio.service';

describe('CitasServicioService', () => {
  let service: CitasServicioService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CitasServicioService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
