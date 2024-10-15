import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PermisoRol, Rol } from 'src/app/models/Roles';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class PermisosService {

  private baseUrl = environment.URL;
  private permiso = "permiso"
  private rol = "rol"

  constructor(private http: HttpClient) { }

  obtenerPermisosRegistrados(): Observable<any> {
    return this.http.get(`${this.baseUrl}/${this.permiso}/obtener-permisos-registrados`,
      // {
      //   headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
      // }
    );
  }

  crearRolYPermiso(rol:PermisoRol): Observable<any> {
    return this.http.post(`${this.baseUrl}/${this.rol}/crear-rol`, rol
      // {
      //   headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
      // }
    );
  }

}
