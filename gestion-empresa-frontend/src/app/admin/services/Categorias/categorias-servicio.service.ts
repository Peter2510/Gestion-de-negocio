import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { categorias_servicios } from 'src/app/models/Servicios';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class CategoriasServicioService {
  private baseUrl = environment.URL;
  private categorias = 'categorias';
  public categoriasElementos = signal<any>([]);
  private idServicio: number | null = null;

  constructor(private http: HttpClient) {
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
    return this.http.get(`${this.baseUrl}/${this.categorias}/obtener-categorias`);
  }

  //funcion para obtener estados
  obtenerEstadosServicios(): Observable<any> {
    return this.http.get(
      `${this.baseUrl}/${this.categorias}/obtenerTodasCategorias`
    );
  }

  obtenerCategoria(id:any): Observable<any>{
    return this.http.get(`${this.baseUrl}/${this.categorias}/obtener-categoria/${id}`)
  }

  actualizarCategoria(categoria:categorias_servicios):Observable<any>{
    return this.http.put(`${this.baseUrl}/${this.categorias}/actualizar-categoria`, categoria);
  }

  crearCategoria(categoria:categorias_servicios):Observable<any>{
    return this.http.post(`${this.baseUrl}/${this.categorias}/crear-categoria`, categoria);
  }

}
