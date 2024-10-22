import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { jwtValidoGuard } from './jwt-valido.guard';

describe('jwtValidoGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => jwtValidoGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
