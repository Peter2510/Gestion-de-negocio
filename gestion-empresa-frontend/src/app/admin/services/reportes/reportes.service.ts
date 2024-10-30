import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ServicioAuthService } from 'src/app/auth/services/servicio-auth.service';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class ReportesService {
  private baseUrl = environment.URL;


  constructor(private http: HttpClient, private authService: ServicioAuthService) { }


  getCitasPorSemana(anio: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/reportes/citas/semana?anio=${anio}`,
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
      });
  }

  getCitasPorMes(anio: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/reportes/citas/mes?anio=${anio}`,
    {
      headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
    });
  }


  getCitasPorAnio(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/reportes/citas/anio`,
    {
      headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
    });
  }

   //obtiene las citas por servicio
   getCitasPorServicio(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/reportes/citas/por-servicio`,
    {
      headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
    });
  }

  //obtiene las citas por estado
  getCitasPorEstado(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/reportes/citas/por-estado`,
    {
      headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
    });
  }

  //obtiene los empleados por rol
  getEmpleadosPorRol(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/reportes/empleados/por-rol`,
    {
      headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
    });
  }

}
