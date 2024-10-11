import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class RolesService {

  private baseUrl = environment.URL;
  private roles = "rol"

  constructor(private http:HttpClient) { }

  obtenerRolesRegistrados(): Observable<any>{
    return this.http.get(`${this.baseUrl}/${this.roles}/obtenerRoles`,
      // {
      //   headers: new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`),
      // }
    );
  }

}
