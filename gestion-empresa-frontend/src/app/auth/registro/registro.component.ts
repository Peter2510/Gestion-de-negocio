import { Component, inject } from '@angular/core';
import { ServicioAuthService } from '../services/servicio-auth.service';
import { Router } from '@angular/router';
import { Genero, Persona } from 'src/app/models/Persona';
import { Usuario } from 'src/app/models/Usuario';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css'],
})
export class RegistroComponent {
  //para las personas
  nombre!: string;
  cui!: number;
  correo!: string;
  direccion!: string;
  nit!: string;
  telefono!: number;
  // ver que es
  genero!: Genero;

  // para los usuarios
  nombreUsuario!: string;
  password!: string;
  confirmacionPassword!: string;
  activo!: boolean;
  a2fActivo!: boolean;

  //servicios
  servicioAuth = inject(ServicioAuthService);
  router = inject(Router);

  //funcion para registrar

  registrar() {
    //generacion de elementos

    let nuevaPersona: Persona = {
      id: 0,
      cui: this.cui,
      direccion: this.direccion,
      nit: this.nit,
      nombre: this.nombre,
      correo: this.correo,
      numero: this.telefono,
      telefono: this.telefono,
      genero: this.genero,
    };

    let nuevoUsuario: Usuario = {
      id: 0,
      nombre_usuario: this.nombreUsuario,
      password: this.password,
      a2f_activo: this.a2fActivo === undefined ? false : true,
      activo: this.activo === undefined ? false : true,
    };

    // aca enviamos el servicio
    this.servicioAuth
      .registro(
        nuevaPersona,
        nuevoUsuario,
        this.password,
        this.nombreUsuario,
        this.a2fActivo
      )
      .subscribe();
    console.log(nuevaPersona, nuevoUsuario, this.genero);
  }
}
