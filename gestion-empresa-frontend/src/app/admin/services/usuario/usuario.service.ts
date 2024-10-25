import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ServicioAuthService } from 'src/app/auth/services/servicio-auth.service';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class UsuarioService {
  private baseUrl = environment.URL;
  private usuarios = 'usuarios';
  private idUsuario: number | null = null;

  constructor(private http: HttpClient, private authService: ServicioAuthService) { }

  setIdUsuario(id: number) {
    this.idUsuario = id;
  }

  getIdUsuario(): number | null {
    return this.idUsuario;
  }

  clearIdUsuario() {
    this.idUsuario = null;
  }

  obtenerEmpleados(): Observable<any> {
    return this.http.get(`${this.baseUrl}/${this.usuarios}/listar-empleados`,
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
      }
    )
  }

  obtenerUsuario(id: number): Observable<any> {
    console.log("usuario obtener", id);
    
    return this.http.get(`${this.baseUrl}/${this.usuarios}/obtener-usuario/${id}`,
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
      }
    )
  }

  actualizarUsuario(usuario): Observable<any> {
    return this.http.put(`${this.baseUrl}/${this.usuarios}/actualizar-usuario`, usuario,
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
      }
    )
  }

  cambiarContrasenia(idUsuario: number, contraseniaActual: string, contraseniaNueva: string): Observable<any> {
    return this.http.put(`${this.baseUrl}/${this.usuarios}/actualizar-contrasenia`, {
      idUsuario,
      contraseniaActual,
      contraseniaNueva
    },
    {
      headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
    }
  );
  }
  

}
