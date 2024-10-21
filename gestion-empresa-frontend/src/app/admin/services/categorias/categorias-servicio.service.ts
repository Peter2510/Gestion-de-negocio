import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { categorias_servicios, estadoServicio } from 'src/app/models/Servicios';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class CategoriasServicioService {
  private baseUrl = environment.URL;
  private categorias = 'categorias';
  private estadoServicio = 'estadoServicio';
  public categoriasElementos = signal<any>([]);
  public estadoServicioElementos = signal<estadoServicio[]>([]);
  private idServicio: number | null = null;

  constructor(private http: HttpClient) {
    this.obtenerEstadosServicios();
  }

  /**Manejo interno del id del rol para no exponerlo en la url */
  seCategoriaId(id: number) {
    this.idServicio = id;
  }

  getCategoriaId(): number | null {
    return this.idServicio;
  }

  clearCategoriaId() {
    this.idServicio = null;
  }

  //funcion para obtener categorias
  obtenerTodasCategoriasRegistradas(): Observable<any> {
    return this.http.get(
      `${this.baseUrl}/${this.categorias}/obtener-categorias`
    );
  }

  //funcion para obtener estados
  obtenerEstadosServicios() {
    this.http
      .get(
        `${this.baseUrl}/${this.estadoServicio}/obtenerTodosEstadosServicios`
      )
      .subscribe({
        next: (data: any) => {
          console.log(data);

          this.estadoServicioElementos.set(data);
        },
        error: (err) => {
          console.error('Error al obtener estados de servicio:', err);
        },
      });
  }

  obtenerCategoria(id: any): Observable<any> {
    return this.http.get(
      `${this.baseUrl}/${this.categorias}/obtener-categoria/${id}`
    );
  }

  actualizarCategoria(categoria: categorias_servicios): Observable<any> {
    return this.http.put(
      `${this.baseUrl}/${this.categorias}/actualizar-categoria`,
      categoria
    );
  }

  crearCategoria(categoria: categorias_servicios): Observable<any> {
    return this.http.post(
      `${this.baseUrl}/${this.categorias}/crear-categoria`,
      categoria
    );
  }
}
