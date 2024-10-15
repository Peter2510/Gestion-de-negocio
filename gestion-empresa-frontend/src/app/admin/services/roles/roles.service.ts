import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class RolesService {

  private baseUrl = environment.URL;
  private roles = "rol"
  private permisoRol = "permiso-rol"
  private rolId: number | null = null;

  constructor(private http:HttpClient) { }

  /**Manejo interno del id del rol para no exponerlo en la url */
  setRolId(id: number) {
    this.rolId = id;
  }

  getRolId(): number | null {
    return this.rolId;
  }

  clearRolId() {
    this.rolId = null;
  }

  obtenerRolesRegistrados(): Observable<any>{
    return this.http.get(`${this.baseUrl}/${this.roles}/obtener-roles`,
      // {
      //   headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
      // }
    );
  }

  obtenerRolYPermisoEspecifico(id:any): Observable<any>{
    return this.http.get(`${this.baseUrl}/${this.permisoRol}/obtener-permiso-rol/${id}`,
      // {
      //   headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
      // }
    );
  }

}
