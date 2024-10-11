import { Component, inject } from '@angular/core';
import { ServicioAuthService } from '../services/servicio-auth.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { HttpStatusCode } from '@angular/common/http';

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
    this.servicioAuth.login(this.nombreUsuario, this.password).subscribe({
      next: (response: any) => {
        console.log(response.mensaje, 'simon');
        console.log(response.ok);

        if (response.ok) {
          this.inicioSesion(response.mensaje);
          console.log(response.mensaje);
          Swal.fire({
            icon: 'success',
            title: 'Sesión Iniciada',
            text: 'Inicio de sesión exitoso.',
          });
        }
      },
      error: (err) => {
        console.log(err);
        switch (err.status) {
          case HttpStatusCode.NotFound:
            Swal.fire({
              icon: 'warning',
              title: 'Usuario no encontrado',
              text: 'El usuario no existe en el sistema.',
            });
            break;

          case HttpStatusCode.BadRequest:
            Swal.fire({
              icon: 'error',
              title: 'Credenciales Incorrectas',
              text: 'Las credenciales no coinciden, por favor verifique.',
            });
            break;
        }
      },
    });
  }

  inicioSesion(token: string) {
    console.log('simon');

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
        this.router.navigate(['/usuarios']);
      } else if (id === 1) {
        this.router.navigate(['/administrador']);
      }
    }
  }
}
