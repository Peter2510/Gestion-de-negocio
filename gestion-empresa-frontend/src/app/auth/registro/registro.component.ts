import { Component, inject } from '@angular/core';
import { ServicioAuthService } from '../services/servicio-auth.service';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css'],
})
export class RegistroComponent {
  nombre!: String;
  cui!: string;
  correo!: string;
  direccion!: string;
  nit!: string;
  numero!: number;
  // ver que es
  id_genero!: number;

  //servicios
  servicioAuth = inject(ServicioAuthService);
}
