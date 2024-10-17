import { Component, OnInit, ViewChild, inject } from '@angular/core';
import { ServicioAuthService } from 'src/app/auth/services/servicio-auth.service';
import { Persona } from 'src/app/models/Persona';
import { Usuario } from 'src/app/models/Usuario';
import { RolesService } from '../../services/roles/roles.service';
import { Rol } from 'src/app/models/Roles';
import { NgForm } from '@angular/forms';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-crear-empleado',
  templateUrl: './crear-empleado.component.html',
  styleUrls: ['./crear-empleado.component.css']
})
export class CrearEmpleadoComponent implements OnInit {

  @ViewChild('registroForm') registroForm!: NgForm;
  servicioAuth = inject(ServicioAuthService);
  roles: Rol[] = [];
  loading: boolean;
  nuevaPersona: Persona = {
    id: 0,
    cui: 0,
    direccion: "",
    nit: "",
    nombre: "",
    correo: "",
    numero: 0,
    telefono: 0,
    genero: {
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
  idRol = 0;

  constructor(private rolService: RolesService) {
  }

  ngOnInit(): void {
    this.obtenerRolesRegistrados();
  }

  obtenerRolesRegistrados() {
    this.rolService.obtenerRolesRegistrados().subscribe({
      next: (data) => {

        this.roles = data.roles

      }, error: (error) => {
        console.log(error)
      }
    })
  }


  registrar() {
    // Verificar si el formulario es válido
    if (!this.registroForm.valid || this.idRol == 0 || this.nuevaPersona.genero.id == 0) {
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
