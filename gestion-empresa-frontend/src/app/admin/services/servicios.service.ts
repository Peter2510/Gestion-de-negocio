import { HttpClient } from '@angular/common/http';
import { Injectable, signal, Signal } from '@angular/core';
import { Observable } from 'rxjs';
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
      todosDatos
    );
  }

  //servicio para obtener todos los servicios de un empleado y sus elementos especificos
  obtenerTodosServicios() {
    this.http
      .get(`${this.baseUrl}/${this.servicios}/obtenerTodosServicios`)
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
    this.http
      .get(
        `${this.baseUrl}/${this.duracionServicioPrestado}/obtenerTodosServiciosEspecificos/${id}`
      )
      .subscribe({
        next: (data: any) => {
          console.log(data);

          this.serviciosEspecificos.set(data.todoServicios);
        },
        error: (err) => {
          console.error('Error al obtener estados de servicio:', err);
        },
      });
  }
}
