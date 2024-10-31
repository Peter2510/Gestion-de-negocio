import { Component, OnInit, inject } from '@angular/core';
import { ServicioAuthService } from '../services/servicio-auth.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-vista2fa-login',
  templateUrl: './vista2fa-login.component.html',
  styleUrls: ['./vista2fa-login.component.css']
})
export class Vista2faLoginComponent implements OnInit{

  servicioAuth = inject(ServicioAuthService);
  private idUsuario: number;
  codigo: string = '';

  constructor(private router:Router) { }
  ngOnInit(): void {
    this.idUsuario = this.servicioAuth.idUsuario2af;
  }

  //validar codigo 2fa validarCodigo2fa
  validarCodigo2fa() {

    if(this.codigo.length !== 6){
      Swal.fire({
        icon: 'error',
        title: 'Código Incorrecto',
        text: 'El código debe contener 6 dígitos.',
      });
      return;

    }

    if(this.idUsuario === undefined){
      this.router.navigate(['/auth/login']);
      return;
    }

    this.servicioAuth.validarCodigo2fa(this.idUsuario, this.codigo).subscribe({
      next: (response: any) => {
        if (response.ok) {
          Swal.fire({
            icon: 'success',
            title: response.mensaje,
          });
          this.inicioSesion(response.token)
        }
      },
      error: (err) => {
        console.log(err);
        Swal.fire({
          icon: 'error',
          title: err.error.mensaje,
        });
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
      } else {
        this.router.navigate(['/administrador/panel-administrador']);
      }
    }
  }

}
