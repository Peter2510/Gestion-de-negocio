import { Component, OnInit } from '@angular/core';
import { Permiso, PermisoRol } from 'src/app/models/Roles';
import { PermisosService } from '../../services/permisos/permisos.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-crear-rol',
  templateUrl: './crear-rol.component.html',
  styleUrls: ['./crear-rol.component.css']
})
export class CrearRolComponent implements OnInit {

  permisos: Permiso[] = [];
  permisosSelecionados: Permiso[] = []
  nuevoRol: PermisoRol = {
    nombre: "",
    descripcion: "",
    permisos: []
  };
  loading: boolean = true;


  constructor(private permisoService: PermisosService) {

  }

  ngOnInit(): void {
    this.permisoService.obtenerPermisosRegistrados().subscribe({
      next: (data) => {
        this.loading = false;
        this.permisos = data.permisos

      }, error: (error) => {
        this.loading = false;
      }
    })
  }

  guardarPermisos() {
    const permisosSeleccionados = this.permisos.filter(permiso => permiso.selected);

    if (permisosSeleccionados.length == 0) {
      Swal.fire({
        title: "Debes seleccionar al menos un permiso",
        icon: "warning"
      })
      return
    }

    //eliminar el atributo 'selected' de cada permiso
    this.permisos.forEach(permiso => {
      delete permiso.selected;
    });

    this.nuevoRol.permisos = permisosSeleccionados;

    this.crearRolPermiso();

  }

  private crearRolPermiso() {
    this.loading = true
    this.permisoService.crearRolYPermiso(this.nuevoRol).subscribe({
      next: (data) => {
        Swal.fire({
          title: `${data.mensaje}`,
          icon: "success"
        })
        this.loading = false
      }, error: (error) => {
        Swal.fire({
          title: `${error.error.mensaje}`,
          icon: "warning"
        })
        this.loading = false
      }
    });
  }

}
