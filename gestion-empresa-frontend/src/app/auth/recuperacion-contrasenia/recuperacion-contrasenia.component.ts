import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ServicioAuthService } from '../services/servicio-auth.service';

@Component({
  selector: 'app-recuperacion-contrasenia',
  templateUrl: './recuperacion-contrasenia.component.html',
  styleUrls: ['./recuperacion-contrasenia.component.css']
})
export class RecuperacionContraseniaComponent {
  email: string = '';
  codigo: string = '';
  nuevaContrasena: string = '';
  verificarContrasena: string = '';
  mensaje: string = '';
  loading: boolean = false;
  paso: number = 1; //1: ingresar correo, 2: ingresar Código, 3: cambiar Contraseña

  constructor(private authService: ServicioAuthService) {}

  // Método para validar el correo electrónico
  validarCorreo() {
    // this.loading = true;
    // this.authService.validarCorreo(this.email)
    //   .subscribe({
    //     next: () => {
    //       this.mensaje = 'Correo válido. Se ha enviado un código de recuperación.';
    //       this.paso = 2; // Cambiar a la siguiente pantalla
    //       this.loading = false;
    //     },
    //     error: (err) => {
    //       this.mensaje = 'Error: ' + err.message;
    //       this.loading = false;
    //     }
    //   });
  }

  // Método para validar el código de recuperación
  validarCodigo() {
    // this.loading = true;
    // this.authService.validarCodigo(this.email, this.codigo)
    //   .subscribe({
    //     next: () => {
    //       this.mensaje = 'Código válido. Puedes cambiar tu contraseña.';
    //       this.paso = 3; // Cambiar a la siguiente pantalla
    //       this.loading = false;
    //     },
    //     error: (err) => {
    //       this.mensaje = 'Código inválido: ' + err.message;
    //       this.loading = false;
    //     }
    //   });
  }

  // Método para cambiar la contraseña
  onSubmit() {
    // if (this.nuevaContrasena !== this.verificarContrasena) {
    //   this.mensaje = 'Las contraseñas no coinciden.';
    //   return;
    // }

    // this.loading = true;
    // this.authService.recuperarContrasena(this.email, this.codigo, this.nuevaContrasena)
    //   .subscribe({
    //     next: () => {
    //       this.mensaje = 'Contraseña actualizada con éxito';
    //       this.loading = false;
    //       // Resetear campos después de éxito
    //       this.resetFields();
    //     },
    //     error: (err) => {
    //       this.mensaje = 'Error al actualizar la contraseña: ' + err.message;
    //       this.loading = false;
    //     }
    //   });
  }

  // Método para resetear campos
  resetFields() {
    this.email = '';
    this.codigo = '';
    this.nuevaContrasena = '';
    this.verificarContrasena = '';
    this.paso = 1; // Regresar al primer paso
  }
}
