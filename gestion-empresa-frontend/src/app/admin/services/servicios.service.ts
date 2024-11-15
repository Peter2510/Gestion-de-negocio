import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable, signal, Signal } from '@angular/core';
import { Observable } from 'rxjs';
import { ServicioAuthService } from 'src/app/auth/services/servicio-auth.service';
import { dias_laborales, jornada_laboral } from 'src/app/models/Jornadas';
import {
  DuracionServicioPrestado,
  servicios,
  serviciosPrestados,
} from 'src/app/models/Servicios';
import { Usuario } from 'src/app/models/Usuario';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class ServiciosService {
  private baseUrl = environment.URL;
  private servicios = 'servicios';
  private duracionServicioPrestado = 'duracionServicioPrestado';
  authService = inject(ServicioAuthService);

  //signal
  public todosServicios = signal<any>([]);
  public serviciosEspecificos = signal<any>([]);
  constructor(private http: HttpClient) {
    this.obtenerTodosServicios();
  }

  //funcion para crear nuevos servicios
  creacionServicio(
    usuario: number,
    nuevoServicio: servicios,
    jornada_laboral: jornada_laboral[],
    diasLaborel: number[],
    serviciosPrestados: serviciosPrestados[],
    duracionServicioPrestado: DuracionServicioPrestado[]
  ): Observable<any> {
    console.log(usuario);
    const todosDatos = {
      idUsuario: usuario,
      servicios: {
        nombre: nuevoServicio.nombre,
        descripcion: nuevoServicio.descripcion,
        imagen: nuevoServicio.imagen,
        idEstadoServicio: { id: Number(nuevoServicio.estadoServicio) }, // Estado servicio
        idTipoServicio: { id: Number(nuevoServicio.categoria) }, // Tipo de servicio
      },
      jornadaLaboral: jornada_laboral.map((jornada) => ({
        nombre: jornada.nombre,
        horaInicio: jornada.hora_inicio,
        horaFin: jornada.hora_fin,
      })),
      diasLaborales: diasLaborel.map((dia) => ({
        id: dia, // Aquí asegúrate que `dia` es el ID numérico, no string.
      })),
      servicioPrestados: serviciosPrestados.map((servicio) => ({
        nombre: servicio.nombre,
        precio: servicio.precio,
        idEstadoServicio: { id: Number(servicio.estadoServicio) }, // Estado del servicio prestado
      })),
      duracionServicioPrestados: duracionServicioPrestado.map((duracion) => ({
        id: duracion.id,
        nombre: duracion.nombre,
        duracion: duracion.duracion,
      })),
    };

    console.log(todosDatos);

    return this.http.post(
      `${this.baseUrl}/${this.servicios}/creacionNuevosServicios`,
      todosDatos,
      {
        headers: new HttpHeaders().set(
          'Authorization',
          `Bearer ${this.authService.getToken()}`
        ),
      }
    );
  }

  //servicio para obtener todos los servicios de un empleado y sus elementos especificos
  obtenerTodosServicios() {
    this.http
      .get(`${this.baseUrl}/${this.servicios}/obtenerTodosServicios`, {
        headers: new HttpHeaders().set(
          'Authorization',
          `Bearer ${this.authService.getToken()}`
        ),
      })
      .subscribe({
        next: (data: any) => {
          console.log(data);

          this.todosServicios.set(data.todoServicios);
        },
        error: (err) => {
          console.error('Error al obtener estados de servicio:', err);
        },
      });
  }

  // servicios especificos
  obtenerTodosServiciosEspecificos(id: number) {
    return this.http.get(
      `${this.baseUrl}/${this.servicios}/obtenerTodosServiciosEspecificos/${id}`,
      {
        headers: new HttpHeaders().set(
          'Authorization',
          `Bearer ${this.authService.getToken()}`
        ),
      }
    );
  }

  // para ver la duracion especifica
  obtenerDuracionServicio(id: number) {
    return this.http.get(
      `${this.baseUrl}/${this.duracionServicioPrestado}/duracionServicioEspecifico/${id}`,
      {
        headers: new HttpHeaders().set(
          'Authorization',
          `Bearer ${this.authService.getToken()}`
        ),
      }
    );
  }
}
