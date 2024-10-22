import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
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

  //signal
  public diasLaboralesTotales = signal<dias_laborales[]>([]);
  public jornadasEspecificas = signal<any[]>([]);
  constructor(private http: HttpClient) {
    this.obtenerDiasLaborales();
  }

  obtenerDiasLaborales() {
    this.http.get(`${this.baseUrl}/${this.dias}/obtenerTodosDias`).subscribe({
      next: (elementos: any) => {
        this.diasLaboralesTotales.set(elementos);
      },
    });
  }

  // jornadas especificos
  obtenerTodasJornadasEspecificos(id: number) {
    this.http
      .get(
        `${this.baseUrl}/${this.jornadasServicios}/obtenerTodasJornadas/${id}`
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
