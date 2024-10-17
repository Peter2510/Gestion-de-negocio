import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class JornadasLaboralesServicioService {
  private baseUrl = environment.URL;
  private dias = 'diasLaborales';
  private rol = 'rol';

  constructor(private http: HttpClient) {}

  obtenerDiasLaborales(): Observable<any> {
    return this.http.get(`${this.baseUrl}/${this.dias}/obtenerTodosDias`);
  }
}
