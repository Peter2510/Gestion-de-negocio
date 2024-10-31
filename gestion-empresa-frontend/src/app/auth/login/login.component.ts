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
      
        if(response.idUsuario){

          Swal.fire({
            title: response.mensaje,
            icon: 'info',
          });

          this.router.navigate(['/auth/Autenticacion2FA']);
          this.servicioAuth.idUsuario2af = response.idUsuario;
          return;

        }else if (response.ok) {
          this.inicioSesion(response.mensaje);
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
              text: 'Verifique sus credenciales y vuelva a intentar de nuevo.',
            });
            break;
        }
      },
    });
  }

  inicioSesion(token: string) {
    this.servicioAuth.saveToken(token);
    const idTipoUsuario = this.servicioAuth.getIdTipoUsuario();
    console.log(idTipoUsuario);

    if (idTipoUsuario !== null) {
      const id = idTipoUsuario;

      if (id === 2) {
        this.router.navigate(['/usuarios/']);
      } else if (id === 12) {
        this.router.navigate(['/empleados/VistaEmpleadoComponent']);
      } else {
        this.router.navigate(['/administrador/panel-administrador']);
      }
    }
  }
}

// <button class="btn">
//   Inbox
//   <div class="badge badge-secondary">+99</div>
// </button>
