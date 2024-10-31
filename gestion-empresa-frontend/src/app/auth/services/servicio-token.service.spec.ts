import { TestBed } from '@angular/core/testing';

import { ServicioTokenService } from './servicio-token.service';

describe('ServicioTokenService', () => {
  let service: ServicioTokenService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServicioTokenService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
