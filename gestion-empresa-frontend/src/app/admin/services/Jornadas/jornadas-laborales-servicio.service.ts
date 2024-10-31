import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { ServicioAuthService } from 'src/app/auth/services/servicio-auth.service';
import { dias_laborales } from 'src/app/models/Jornadas';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class JornadasLaboralesServicioService {
  private baseUrl = environment.URL;
  private dias = 'diasLaborales';
  private jornadasServicios = 'jornadaServicio';
  private rol = 'rol';

  authService = inject(ServicioAuthService);

  //signal
  public diasLaboralesTotales = signal<dias_laborales[]>([]);
  public jornadasEspecificas = signal<any[]>([]);
  constructor(private http: HttpClient) {
    this.obtenerDiasLaborales();
  }

  obtenerDiasLaborales() {
    this.http.get(`${this.baseUrl}/${this.dias}/obtenerTodosDias`,
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
      }
    ).subscribe({
      next: (elementos: any) => {
        this.diasLaboralesTotales.set(elementos);
      },
    });
  }

  // jornadas especificos
  obtenerTodasJornadasEspecificos(id: number) {
    this.http
      .get(
        `${this.baseUrl}/${this.jornadasServicios}/obtenerTodasJornadas/${id}`,
        {
          headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
        }
      )
      .subscribe({
        next: (data: any) => {
          console.log(data);

          this.jornadasEspecificas.set(data.todoServicios);
        },
        error: (err) => {
          console.error('Error al obtener estados de servicio:', err);
        },
      });
  }
}
