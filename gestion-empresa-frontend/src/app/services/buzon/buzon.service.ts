import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ServicioAuthService } from 'src/app/auth/services/servicio-auth.service';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class BuzonService {

  private baseUrl = environment.URL;
  private buzon = 'buzon';


  constructor(private http: HttpClient, private authService: ServicioAuthService) {}

  obtenerNotificaciones(id:any):Observable<any>{
    return this.http.get(`${this.baseUrl}/${this.buzon}/obtener-buzon-usuario/${id}`,
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
      }
    );
  }

  actualizarNotificacion(id:any):Observable<any>{
    return this.http.put(`${this.baseUrl}/${this.buzon}/actualizar-notificacion/${id}`, {},
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
      }
    );
  }

}
