import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class CategoriasServicioService {
  private baseUrl = environment.URL;
  private categorias = 'categorias';
  public categoriasElementos = signal<any>([]);

  constructor(private http: HttpClient) {
    this.obtenerCategoriasRegistradas();
    console.log('aaa');
  }

  //funcion para obtener categorias
  obtenerCategoriasRegistradas() {
    this.http
      .get<any>(`${this.baseUrl}/${this.categorias}/obtenerTodasCategorias`)
      .pipe(
        tap((elementos: any) => {
          console.log(elementos, '----------------------');
          this.categoriasElementos.set(elementos);
        })
      );
  }

  //funcion para obtener categorias
  obtenerTodasCategoriasRegistradas() {
    return this.http.get(
      `${this.baseUrl}/${this.categorias}/obtenerTodasCategorias`
    );
  }

  //funcion para obtener estados
  obtenerEstadosServicios(): Observable<any> {
    return this.http.get(
      `${this.baseUrl}/${this.categorias}/obtenerTodasCategorias`
    );
  }
}
