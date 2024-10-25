import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CitasServicioService {
  private baseUrl = environment.URL;
  private citas = 'citas';

  constructor(private http: HttpClient) {}

  //funcion para obtener las citas
  obtenerCitas(): Observable<any> {
    return this.http.get(`${this.baseUrl}/${this.citas}/obtener-Citas`);
  }
}
