import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { ServicioAuthService } from 'src/app/auth/services/servicio-auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CitasServicioService {
  private baseUrl = environment.URL;
  private citas = 'citas';
  authService = inject(ServicioAuthService);

  constructor(
    private http: HttpClient,
    private servicioCookie: ServicioAuthService
  ) { }

  //funcion para obtener las citas
  obtenerCitas(): Observable<any> {
    return this.http.get(`${this.baseUrl}/${this.citas}/obtener-Citas`, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
    });
  }

  //funcion para obtener las citas por id
  obtenerCitasId(): Observable<any> {
    console.log(this.servicioCookie.getIdUsuario());

    return this.http.get(
      `${this.baseUrl}/${this.citas
      }/obtenerTodasCitasEspecificos/${this.servicioCookie.getIdUsuario(),
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
      }
      }`
    );
  }
  //funcion para crear nuevas citas
  crearCitas(
    hora_inicio: any,
    hora_fin: any,
    idUsuario: any,
    idDiaLaboral: any,
    idServicio: any,
    idserviciosEspecificos: any[]
  ): Observable<any> {
    const elementos = {
      horaInicio: hora_inicio,
      horaFin: hora_fin,
      idUsuario: this.servicioCookie.getIdUsuario(),
      idDiaLaboral: idDiaLaboral,
      idServicio: idServicio,
      listadoServiciosEspecificos: idserviciosEspecificos,
    };

    console.log(
      'Cita Data:',
      JSON.stringify(elementos),
      hora_inicio,
      elementos
    );
    return this.http.post(
      `${this.baseUrl}/${this.citas}/crearCita`,
      elementos,
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
      }
    );
  }
}
