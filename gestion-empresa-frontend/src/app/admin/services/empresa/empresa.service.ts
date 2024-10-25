import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { Empresa } from '../../../models/Empresa';
import { ServicioAuthService } from 'src/app/auth/services/servicio-auth.service';

@Injectable({
  providedIn: 'root',
})
export class EmpresaService {
  private baseURL = environment.URL;
  private empresa = 'empresa';
  private tipoServicio = 'tipoServicio';
  private tipoAsignacionCita = 'tipoAsignacionCita';

  //aca que se quede de una como token

  constructor(
    private http: HttpClient,
    private authService: ServicioAuthService
  ) {}

  public obtenerInfoEmpresa(): Observable<any> {
    return this.http.get<any>(`${this.baseURL}/${this.empresa}/obtener-empresa/1`,
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
      }
    );
  }

  public obtenerTipoServicios(): Observable<any> {
    return this.http.get<any>(`${this.baseURL}/${this.tipoServicio}/obtener-tipos-servicio`,
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
      }
    );
  }

  public obtenerTipoAsignacionCita(): Observable<any> {
    return this.http.get<any>(`${this.baseURL}/${this.tipoAsignacionCita}/obtener-tipos-asignacion-cita`,
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
      }
    );
  }

  public actualizarEmpresa(empresa: any, logo: any): Observable<any> {
    const formData = new FormData();
    formData.append('nombre', empresa.nombre);
    formData.append('descripcion', empresa.descripcion);
    formData.append('direccion', empresa.direccion);
    formData.append('telefono', empresa.telefono);
    formData.append('email', empresa.email);
    formData.append('idTipoServicio', empresa.tipoServicio.id);
    formData.append('idTipoAsignacionCita', empresa.tipoAsignacionCita.id);
    if (logo) {
      formData.append('logoFile', logo);
    }

    return this.http.post<any>(`${this.baseURL}/${this.empresa}/actualizar-empresa/1`, formData,
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
      }
    );
  }
}
