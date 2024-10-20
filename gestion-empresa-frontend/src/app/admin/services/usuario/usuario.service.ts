import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private baseUrl = environment.URL;
  private usuarios = "usuarios";
  private idUsuario: number | null = null;

  constructor(private http: HttpClient) { }

  setIdUsuario(id: number) {
    this.idUsuario = id;
  }

  getIdUsuario(): number | null {
    return this.idUsuario;
  }

  clearIdUsuario() {
    this.idUsuario = null;
  }

  obtenerEmpleados():Observable<any>{
    return this.http.get(`${this.baseUrl}/${this.usuarios}/listar-empleados`)
  }

  obtenerUsuario(id: number):Observable<any>{
    return this.http.get(`${this.baseUrl}/${this.usuarios}/obtener-usuario/${id}`)
  }
}
