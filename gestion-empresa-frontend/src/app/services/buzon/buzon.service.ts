import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class BuzonService {

  private baseUrl = environment.URL;
  private buzon = 'buzon';


  constructor(private http: HttpClient) {}

  obtenerNotificaciones(id:any):Observable<any>{
    return this.http.get(`${this.baseUrl}/${this.buzon}/obtener-buzon-usuario/${id}`);
  }

  actualizarNotificacion(id:any):Observable<any>{
    return this.http.put(`${this.baseUrl}/${this.buzon}/actualizar-notificacion/${id}`, {});
  }

}
