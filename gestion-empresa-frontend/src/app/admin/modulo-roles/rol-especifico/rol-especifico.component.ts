import { Component, OnInit } from '@angular/core';
import { RolesService } from '../../services/roles/roles.service';
import { InfoPermiso, Permiso, Rol } from 'src/app/models/Roles';
import { PermisosService } from '../../services/permisos/permisos.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-rol-especifico',
  templateUrl: './rol-especifico.component.html',
  styleUrls: ['./rol-especifico.component.css']
})
export class RolEspecificoComponent implements OnInit{

  rolId: number | null = null;
  loading: boolean = true;
  rol: Rol = {
    id: 0,
    nombre: "",
    descripcion: ""
  };

  permisosActuales: InfoPermiso[] = [];
  permisosRegistrados: Permiso[] = [];
  
  constructor(private rolService: RolesService, private permisoService: PermisosService, private router: Router) {}

  ngOnInit() {
    this.rolId = this.rolService.getRolId();
    if (this.rolId) {
      this.mostrarInfoRol();
      this.mostrarPermisosRegistrados();
    } else {
      this.router.navigate(['administrador/roles-registrados']);
      this.loading = false;
    }
  }

  mostrarInfoRol() {
    console.log("entro el id----", this.rolId)
    this.rolService.obtenerRolYPermisoEspecifico(this.rolId).subscribe({
      next: (data) => {
        console.log("entro el id", this.rolId)
        this.loading = false;
        this.rol = data.rol;
        this.permisosActuales = data.permisos;
        console.log(this.rol, this.permisosActuales);
        this.asignarPermisosSeleccionados();
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
        console.log(data);
        this.permisosRegistrados = data.permisos;
        console.log(this.permisosRegistrados);
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
}
