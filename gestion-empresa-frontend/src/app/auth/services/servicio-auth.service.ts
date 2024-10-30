import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Observable, tap } from 'rxjs';
import { Genero, Persona } from 'src/app/models/Persona';
import { Usuario } from 'src/app/models/Usuario';
import { environment } from 'src/environments/environment.development';
import { jwtDecode } from 'jwt-decode';
import { InfoPermiso } from 'src/app/models/Roles';
import { RolesService } from 'src/app/admin/services/roles/roles.service';
@Injectable({
  providedIn: 'root',
})
export class ServicioAuthService {
  private readonly directiva = 'auth';
  private readonly url = environment.URL;
  permisos: InfoPermiso[] = [];
  private correoUrl = "correo";
  public idUsuario2af: number;


  //creacion de signal
  public generos = signal<Genero[]>([]);
  private cookieName = 'token';

  constructor(
    private http: HttpClient,
    private cookieService: CookieService,
    private router: Router
  ) {
    this.obtenerGeneros();
  }

  async obtenerPermisos(): Promise<void> {
    try {
      const tipoUsuarioId = this.getIdTipoUsuario();
      const data = await this.http
        .get<{ permisos: InfoPermiso[] }>(`${this.url}/permiso-rol/obtener-permiso-rol/${tipoUsuarioId}`,
          {
            headers: new HttpHeaders().set('Authorization', `Bearer ${this.getToken()}`),
          }
        )
        .toPromise();

      // Verifica si data y data.permisos están definidos
      if (data && data.permisos) {
        this.permisos = data.permisos;
      } else {
        console.error("No se encontraron permisos o el formato es incorrecto.");
      }
    } catch (error) {
      console.error("Error al obtener permisos:", error);
    }
  }

  async tienePermiso(permisoId: number): Promise<boolean> {
    if (this.permisos.length === 0) {
      await this.obtenerPermisos();
    }
    console.log(this.permisos, "desde servicio");
    return this.permisos.some(permiso => permiso.permisoId === permisoId);
  }

  // para el ingreso de los usuarios
  login(nombreUsuario: string, password: string) {
    const body = { nombreUsuario, password };
    return this.http.post(`${this.url}/Auth/login`, body);
  }

  //validar codigo 2fa
  validarCodigo2fa(idUsuario:number, codigo: string): Observable<any> {
    return this.http.post(`${this.url}/Auth/validar-codigo-a2f`, { idUsuario, codigo });
  }

  // para registrar nuevos usuarios
  registro(
    persona: Persona,
    nombreUsuario: string,
    password: string,
    idGenero: number,
    idRol: number,
    a2fActivo: boolean,
    activo: boolean,
    correo: string
  ): Observable<any> {
    const body = { persona, nombreUsuario, password, idGenero, idRol, a2fActivo, activo, correo};
    return this.http.post(`${this.url}/Auth/registro`, body);
  }

  // para Obtener generos
  obtenerGeneros() {
    this.http
      .get<Genero[]>(`${this.url}/Genero`)
      .pipe(
        tap((elementos: Genero[]) => {
          this.generos.set(elementos);
        })
      )
      .subscribe();
  }

  generarCodigoRecuperacionContrasenia(correo:string): Observable<any>{
    const recuperarContrasenia = {
      correo: correo
    }
    return this.http.post<any>(`${this.url}/${this.correoUrl}/recuperar-contrasenia`, recuperarContrasenia);
  }

  validarCodigo(correo:string, codigo:string): Observable<any>{
    const recuperarContrasenia = {
      correo: correo,
      codigo: codigo
    }
    return this.http.post<any>(`${this.url}/${this.correoUrl}/validar-codigo-recuperacion-contrasenia`, recuperarContrasenia);
  }

  cambiarContrasenia(idUsuario:number, contraseniaNueva:string){
    return this.http.post<any>(`${this.url}/${this.correoUrl}/cambio-contrasenia`,{
      idUsuario,
      contraseniaNueva
    });
  }

  // elementos para las cookies
  //guardo Token en cookie
  saveToken(token: string): void {
    this.cookieService.set(this.cookieName, token, undefined, '/');
  }

  //obtengo todo el token jwt
  public getToken(): string | null {
    return this.cookieService.get(this.cookieName);
  }

  //decodifico el token y devuelvo el objeto
  private decodeToken(): any {
    const token = this.getToken();
    if (token) {
      return jwtDecode(token);
    }
    return null;
  }

  //cerrar sesion -> eliminar la cookie
  logout(): void {
    this.cookieService.delete(this.cookieName, '/');
    this.router.navigate(['/']);
  }

  getIdUsuario(): number | null {
    const decodedToken = this.decodeToken();
    if (decodedToken && decodedToken.sub) {
      return decodedToken.sub;
    }
    return null;
  }

  public getIdTipoUsuario(): number | null {
    const decodedToken = this.decodeToken();
    if (decodedToken && decodedToken.rol.id) {
      console.log(decodedToken.rol.id, '---------');

      return decodedToken.rol.id;
    }
    return null;
  }

  getNombreUsuario(): string | null {
    const decodedToken = this.decodeToken();
    if (decodedToken && decodedToken.nombreUsuario) {
      return decodedToken.nombreUsuario;
    }
    return null;
  }

  getIATToken(): string | null {
    const decodedToken = this.decodeToken();
    if (decodedToken && decodedToken.iat) {
      return decodedToken.iat;
    }
    return null;
  }

  getExpToken(): string | null {
    const decodedToken = this.decodeToken();
    if (decodedToken && decodedToken.exp) {
      return decodedToken.exp;
    }
    return null;
  }
  getA2f(): boolean | null {
    const decodedToken = this.decodeToken();
    if (decodedToken && decodedToken.a2fActivo) {
      return decodedToken.a2fActivo;
    }
    console.log(decodedToken.a2fActivo);
    return null;
  }

  getNombre(): boolean | null {
    const decodedToken = this.decodeToken();
    console.log(decodedToken);

    if (decodedToken && decodedToken.nombreUsuario) {
      return decodedToken.nombreUsuario;
    }
    return null;
  }

  getDirecccion(): boolean | null {
    const decodedToken = this.decodeToken();
    if (decodedToken && decodedToken.direccion) {
      return decodedToken.direccion;
    }
    return null;
  }

  getFechaCreacion(): boolean | null {
    const decodedToken = this.decodeToken();
    if (decodedToken && decodedToken.fechaCreacion) {
      return decodedToken.fechaCreacion;
    }
    return null;
  }
}
