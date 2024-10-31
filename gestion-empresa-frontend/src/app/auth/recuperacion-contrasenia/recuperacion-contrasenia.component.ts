import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ServicioAuthService } from '../services/servicio-auth.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment.development';

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
  idUsuario=0;
  private readonly url = environment.URL;

  constructor(private authService: ServicioAuthService, private router:Router) {}

  //validar el correo electrónico
  validarCorreo() {

    if(this.email.length==0){
      Swal.fire({
        title: 'Debes llenar el campo de correo electronico',
        icon: 'info'
      });
      return;
    }

    this.loading = true;
    this.authService.generarCodigoRecuperacionContrasenia(this.email)
      .subscribe({
        next: (data) => {
          Swal.fire({
            title: data.mensaje,
            icon: 'success'
          })
          this.paso = 2;
          this.loading = false;
        },
        error: (err) => {
          Swal.fire({
            title: err.error.mensaje,
            icon: 'error'
          });
          this.loading = false;
        }
      });
  }

  //método para validar el código de recuperación
  validarCodigo() {

    if(this.codigo.length==0){
      Swal.fire({
        title: 'Debes llenar el campo del código',
        icon: 'info'
      });
      return;
    }

    this.loading = true;
    this.authService.validarCodigo(this.email, this.codigo)
      .subscribe({
        next: (data) => {
          Swal.fire({
            title: data.mensaje,
            icon: 'success'
          });
          this.idUsuario = data.idUsuario
          console.log(this.idUsuario, "idididid")
          this.paso = 3;
          this.loading = false;
        },
        error: (err) => {
          Swal.fire({
            title: err.error.mensaje,
            icon: 'error'
          });
          this.loading = false;
        }
      });
  }

  // cambiar la contraseña
  onSubmit() {
    
    if (this.nuevaContrasena.length < 8 || this.verificarContrasena.length < 8) {
      Swal.fire({
        title: 'Las contraseñas deben tener 8 caracteres como mínimo',
        icon: 'info'
      });
      return;
    }

    if (this.nuevaContrasena !== this.verificarContrasena) {
      Swal.fire({
        title: 'Las contraseñas no coinciden',
        icon: 'info'
      })
      return;
    }

    this.loading = true;
    this.authService.cambiarContrasenia(this.idUsuario, this.nuevaContrasena)
      .subscribe({
        next: (data) => {
          Swal.fire({
            title: data.mensaje,
            icon: 'success'
          }).then(()=>{
            this.router.navigate([`${this.url}/auth/login`]);
          });
          this.loading = false;
          this.resetFields();
        },
        error: (err) => {
          Swal.fire({
            title: err.error.mensaje,
            icon: 'error'
          });
          this.loading = false;
        }
      });
  }

  //metodo para resetear campos
  resetFields() {
    this.email = '';
    this.codigo = '';
    this.nuevaContrasena = '';
    this.verificarContrasena = '';
  }
}
