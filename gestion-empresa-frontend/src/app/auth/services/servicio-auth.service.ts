import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ServicioAuthService {
  private readonly directiva = 'auth';

  constructor(private http: HttpClient) {}

  login() {}

  registro() {}
}
