import { Component, inject, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { RolesService } from 'src/app/admin/services/roles/roles.service';
import { UsuarioService } from 'src/app/admin/services/usuario/usuario.service';
import { ServicioAuthService } from 'src/app/auth/services/servicio-auth.service';
import { ActualizacionUsuario } from 'src/app/models/ActualizacionUsuario';
import { InfoPermiso, Rol } from 'src/app/models/Roles';
import { Usuario } from 'src/app/models/Usuario';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-edicion-perfil',
  templateUrl: './edicion-perfil.component.html',
  styleUrls: ['./edicion-perfil.component.css']
})
export class EdicionPerfilComponent implements OnInit {

  @ViewChild('registroForm') registroForm!: NgForm;
  roles: Rol[] = [];
  permisos: InfoPermiso[] = [];
  loading: boolean = true;
  idUsuario: any;
  servicioAuth = inject(ServicioAuthService);
  actualizacion: ActualizacionUsuario;

  usuario: Usuario = {
    id: 0,
    persona: {
      id: 0,
      cui: 0,
      direccion: "",
      nit: "",
      nombre: "",
      correo: "",
      telefono: 0,
      genero: {
        id: 0,
        genero: ""
      }
    },
    rol: {
      id: 0,
      nombre: "",
      descripcion: ""
    },
    nombreUsuario: "",
    password: "",
    a2f_activo: false,
    activo: false
  };

  // Propiedades para la contraseña
  passwordActual: string = '';
  nuevaPassword: string = '';
  confirmarPassword: string = '';

  constructor(private rolService: RolesService, private usuarioService: UsuarioService, private router: Router,
              private token: ServicioAuthService) { }

  ngOnInit(): void {
    this.idUsuario = this.token.getIdUsuario();
    console.log(this.idUsuario, "idUsuario");
    
    this.obtenerInfoUsuario();
  }

  obtenerInfoUsuario() {
    this.usuarioService.obtenerUsuario(this.idUsuario).subscribe({
      next: (data) => {
        this.usuario = data.usuario;
        this.loading = false;
      }, error: (error) => {
        Swal.fire({
          title: error.error.mensaje
        });
        console.log(error);
        this.loading = false;
      }
    });
  }

  actualizarUsuario() {
    this.configurarPersona();
    this.loading = true;

    this.usuarioService.actualizarUsuario(this.actualizacion).subscribe({
      next: (data) => {
        Swal.fire({
          title: data.mensaje,
          icon: 'success'
        });
        this.loading = false;
      }, error: (error) => {
        Swal.fire({
          title: error.error.mensaje,
          icon: 'error'
        });
        this.loading = false;
        console.log(error);
      }
    });
  }

  configurarPersona() {
    this.actualizacion = {
      idUsuario: this.usuario.id,
      nombreUsuario: this.usuario.nombreUsuario,
      idRol: this.usuario.rol.id,
      idGenero: this.usuario.persona.genero.id,
      persona: {
        cui: this.usuario.persona.cui,
        direccion: this.usuario.persona.direccion,
        nit: this.usuario.persona.nit,
        nombre: this.usuario.persona.nombre,
        telefono: this.usuario.persona.telefono,
      },
      correo: this.usuario.persona.correo,
      activo: this.usuario.activo
    };
  }

  cambiarContrasena() {
    if (this.nuevaPassword !== this.confirmarPassword) {
      Swal.fire({
        title: 'Las contraseñas no coinciden',
        icon: 'error'
      });
      return;
    }
  
    this.loading = true;
  
    console.log(this.usuario.id, this.passwordActual, this.nuevaPassword);

    this.usuarioService.cambiarContrasenia(this.usuario.id, this.passwordActual, this.nuevaPassword).subscribe({
      next: (data) => {
        Swal.fire({
          title: data.mensaje,
          icon: 'success'
        });
        this.loading = false;
        this.passwordActual = '';
        this.nuevaPassword = '';
        this.confirmarPassword = '';
      },
      error: (error) => {
        Swal.fire({
          title: error.error.mensaje,
          icon: 'error'
        });
        this.loading = false;
      }
    });
  }
}