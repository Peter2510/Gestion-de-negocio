import { Component, ViewChild, inject } from '@angular/core';
import { ServicioAuthService } from '../services/servicio-auth.service';
import { Router } from '@angular/router';
import { Genero, Persona } from 'src/app/models/Persona';
import { Usuario } from 'src/app/models/Usuario';
import Swal from 'sweetalert2';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css'],
})
export class RegistroComponent {
  
  @ViewChild('registroForm') registroForm!: NgForm;
  loading: boolean;

  nuevaPersona: Persona = {
    id: 0,
    cui: 0,
    direccion: "",
    nit: "",
    nombre: "",
    correo: "",
    telefono: 0,
    genero : {
      id: 0,
      genero: ""
    }
  };

  
  id = 0;
  nombre_usuario = "";
  password = "";
  a2f_activo = false;
  activo = true;
  confirmacionPassword = "";

  //servicios
  servicioAuth = inject(ServicioAuthService);
  router = inject(Router);

  //funcion para registrar

  registrar() {
    // Verificar si el formulario es válido
    if (!this.registroForm.valid) {
      Swal.fire({
        title: "Campos incompletos",
        text: "Por favor, complete todos los campos.",
        icon: "error"
      });
      return;
    }
  
    this.loading = true;
  
    this.servicioAuth.registro(this.nuevaPersona, this.nombre_usuario, this.password, 
      this.nuevaPersona.genero.id, 2, this.a2f_activo, true, this.nuevaPersona.correo).subscribe({
      next: (data) => {
        this.loading = false;
        Swal.fire({
          title: "Registrado correctamente",
          text: data.mensaje,
          icon: "success"
        });
      },
      error: (error) => {
        this.loading = false;
        Swal.fire({
          title: error.error.mensaje,
          icon: "info"
        });
      }
    });
  }
}
