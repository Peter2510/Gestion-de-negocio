import { Component, inject, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ServicioAuthService } from 'src/app/auth/services/servicio-auth.service';
import { ActualizacionUsuario } from 'src/app/models/ActualizacionUsuario';
import { InfoPermiso, Rol } from 'src/app/models/Roles';
import { Usuario } from 'src/app/models/Usuario';
import { RolesService } from '../../services/roles/roles.service';
import { UsuarioService } from '../../services/usuario/usuario.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-perfil-usuario',
  templateUrl: './perfil-usuario.component.html',
  styleUrls: ['./perfil-usuario.component.css']
})
export class PerfilUsuarioComponent implements OnInit{

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
  }

  constructor(private rolService: RolesService, private usuarioService: UsuarioService, private router: Router,
    private token: ServicioAuthService) {
  }

  ngOnInit(): void {
    this.idUsuario = this.token.getIdUsuario();
    console.log(this.idUsuario, "idUsuario");
    
    this.obtenerInfoUsuario();
    this.obtenerRolesRegistrados();
  }

  obtenerRolesRegistrados() {
    this.rolService.obtenerRolesRegistrados().subscribe({
      next: (data) => {
        this.roles = data.roles
      }, error: (error) => {
        console.log(error)
        this.loading = false;
      }
    })
  }

  obtenerInfoUsuario() {
    this.usuarioService.obtenerUsuario(this.idUsuario).subscribe({
      next: (data) => {
        this.usuario = data.usuario;
        this.loading = false;
      }, error: (error) => {
        Swal.fire({
          title: error.error.mensaje
        })
        console.log(error)
        this.loading = false;
      }
    })
  }

  actualizarUsuario() {
    this.configurarPersona();
    this.loading = true;

    this.usuarioService.actualizarUsuario(this.actualizacion).subscribe({
      next: (data) => {
        Swal.fire({
          title: data.mensaje,
          icon: 'success'
        })
        this.loading = false;
      }, error: (error) => {
        Swal.fire({
          title: error.error.mensaje,
          icon: 'error'
        })
        this.loading = false;
        console.log(error)
      }
    })
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
    }
  }

  tienePermiso(permisoId: number): boolean {
    return this.permisos.some(permiso => permiso.permisoId === permisoId);
  }


}
