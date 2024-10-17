import { TestBed } from '@angular/core/testing';

import { CategoriasServicioService } from './categorias-servicio.service';

describe('CategoriasServicioService', () => {
  let service: CategoriasServicioService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CategoriasServicioService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
