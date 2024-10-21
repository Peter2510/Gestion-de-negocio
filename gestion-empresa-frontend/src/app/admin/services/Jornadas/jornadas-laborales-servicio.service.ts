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
  private rol = 'rol';

  //signal
  public diasLaboralesTotales = signal<dias_laborales[]>([]);
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
}
