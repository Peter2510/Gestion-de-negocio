import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { Empresa } from '../../models/Empresa';
import { ServicioAuthService } from 'src/app/auth/services/servicio-auth.service';

@Injectable({
  providedIn: 'root'
})
export class EmpresaService {

  private baseURL = environment.URL;
  
  constructor(private http: HttpClient, private authService:ServicioAuthService ) { }

  public obtenerInfoEmpresa(): Observable<any> {
    return this.http.get<any>(`${this.baseURL}/empresa/obtenerEmpresa/1`,
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
      }
    );
  }

}
