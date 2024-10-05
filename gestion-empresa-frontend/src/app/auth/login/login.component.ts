import { Component, inject } from '@angular/core';
import { ServicioAuthService } from '../services/servicio-auth.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

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

  constructor(private router: Router) {}

  login() {
    this.servicioAuth
      .login(this.nombreUsuario, this.password)
      .subscribe((response: any) => {
        if (response.token == undefined) {
          return;
        }
        this.inicioSesion(response.token);
      });
  }

  inicioSesion(token: string) {
    this.servicioAuth.saveToken(token);

    const message = `Bienvenido, ${this.servicioAuth.getNombre()}!`;

    Swal.fire({
      icon: 'success',
      title: 'Inicio de sesión exitoso ',
      text: message,
    });

    const idTipoUsuario = this.servicioAuth.getIdTipoUsuario();
    console.log(idTipoUsuario);

    if (idTipoUsuario !== null) {
      const id = idTipoUsuario;

      if (id === 2) {
        this.router.navigate(['/administrador']);
      } else if (id === 5) {
        this.router.navigate(['/usuarios']);
      }
    }
  }
}
