import { Component, inject } from '@angular/core';
import { ServicioAuthService } from '../services/servicio-auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  //valores para el login
  nombreUsuario!: string;
  password!: string;
  servicioAuth = inject(ServicioAuthService);

  login() {
    this.servicioAuth
      .login(this.nombreUsuario, this.password)
      .subscribe((response: any) => {
        console.log(response.token);
        if (response.token == undefined) {
          console.log('holas que hace');
          return;
        }
      });
  }
}
