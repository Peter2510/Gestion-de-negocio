import { HttpClient } from '@angular/common/http';
import { Injectable, signal, Signal } from '@angular/core';
import { Observable } from 'rxjs';
import { dias_laborales, jornada_laboral } from 'src/app/models/Jornadas';
import {
  DuracionServicioPrestado,
  servicios,
  serviciosPrestados,
} from 'src/app/models/Servicios';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class ServiciosService {
  private baseUrl = environment.URL;
  private servicios = 'servicios';

  //signal
  public todosServicios = signal<any>([]);
  constructor(private http: HttpClient) {}

  //funcion para crear nuevos servicios
  creacionServicio(
    nuevoServicio: servicios,
    jornada_laboral: jornada_laboral[],
    diasLaborel: number[],
    serviciosPrestados: serviciosPrestados[],
    duracionServicioPrestado: DuracionServicioPrestado[]
  ): Observable<any> {
    const todosDatos = {
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
      .get(`${this.baseUrl}/${this.servicios}/obtenerServiciosGenerales`)
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
}
