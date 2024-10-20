import { Component, OnInit } from '@angular/core';
import { RolesService } from '../../services/roles/roles.service';
import { InfoPermiso, Permiso, Rol } from 'src/app/models/Roles';
import { PermisosService } from '../../services/permisos/permisos.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { ServicioAuthService } from 'src/app/auth/services/servicio-auth.service';

@Component({
  selector: 'app-rol-especifico',
  templateUrl: './rol-especifico.component.html',
  styleUrls: ['./rol-especifico.component.css']
})
export class RolEspecificoComponent implements OnInit {

  rolIdInfo: number | null = null;
  idRolUsuario: any;
  
  loading: boolean = true;
  rol: Rol = {
    id: 0,
    nombre: "",
    descripcion: ""
  };

  //permisos que tiene actualmente el rol
  permisosActuales: InfoPermiso[] = [];
  //permisos registrados dentro de la plataforma
  permisosRegistrados: Permiso[] = [];
  //permisos que tiene el rol para poder editar los roles
  permisos: InfoPermiso[] = [];
  
  constructor(private rolService: RolesService, private permisoService: PermisosService, private router: Router,
    private authService: ServicioAuthService) { }

  ngOnInit() {
    this.idRolUsuario = this.authService.getIdTipoUsuario();
    this.obtenerPermisosSegunRolActual();
    this.rolIdInfo = this.rolService.getRolId();
    if (this.rolIdInfo) {
      this.mostrarInfoRol();
      this.mostrarPermisosRegistrados();
      this.rolService.clearRolId();
    } else {
      this.router.navigate(['administrador/roles-registrados']);
      this.loading = false;
    }
  }

  mostrarInfoRol() {
    this.rolService.obtenerRolYPermisoEspecifico(this.rolIdInfo).subscribe({
      next: (data) => {
        this.loading = false;
        this.rol = data.rol;
        this.permisosActuales = data.permisos;
        this.asignarPermisosSeleccionados();
        console.log(data)
      },
      error: (error) => {
        this.loading = true;
        console.log(error);
      }
    });
  }

  mostrarPermisosRegistrados() {
    this.permisoService.obtenerPermisosRegistrados().subscribe({
      next: (data) => {
        this.loading = false;
        this.permisosRegistrados = data.permisos;
      },
      error: (error) => {
        this.loading = false;
        console.log(error);
      }
    });
  }

  private asignarPermisosSeleccionados() {
    if (this.permisosActuales.length > 0 && this.permisosRegistrados.length > 0) {
      this.permisosRegistrados.forEach(permiso => {
        const encontrado = this.permisosActuales.find(p => p.permisoId === permiso.id);
        permiso.selected = !!encontrado;
      });
    }
  }



  actualizarRol() {

    if (this.rol.nombre.length <=0 || this.rol.descripcion.length <=0) {
      Swal.fire({
        title: "El nombre y descripción del rol son obligatorios",
        icon: "warning"
      });
      return;
    }

    const permisosSeleccionados = this.permisosRegistrados.filter(permiso => permiso.selected);

    if (permisosSeleccionados.length === 0) {
      Swal.fire({
        title: "Debes seleccionar al menos un permiso",
        icon: "warning"
      });
      return;
    }
    this.loading = true;
    this.rolService.actualizarPermisosEInfoRol(permisosSeleccionados,this.rol.id, this.rol.nombre, this.rol.descripcion).subscribe({
      next: (data)=>{
        this.loading = false;
        
        Swal.fire({
          title: data.mensaje,
          icon: "success"
        }).then(()=>{
          window.location.reload()
        });
      },
      error: (error) =>{
        this.loading = false;
        Swal.fire({
          title: error.error.mensaje,
          icon: "error"
        })
      }
    })

  }

  obtenerPermisosSegunRolActual() {
    this.rolService.obtenerRolYPermisoEspecifico(this.idRolUsuario).subscribe({
      next: (data) => {
        this.loading = false;
        this.permisos = data.permisos
      },
      error: (error) => {
        this.loading = true;
        console.log(error);
      }
    });
  }

  tienePermiso(permisoId: number): boolean {
    return this.permisos.some(permiso => permiso.permisoId === permisoId);
  }


}
