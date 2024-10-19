import { Component } from '@angular/core';
import { InfoPermiso } from 'src/app/models/Roles';
import { categorias_servicios } from 'src/app/models/Servicios';
import { CategoriasServicioService } from '../../services/Categorias/categorias-servicio.service';
import { ServicioAuthService } from 'src/app/auth/services/servicio-auth.service';
import { Router } from '@angular/router';
import { RolesService } from '../../services/roles/roles.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-categoria-especifica',
  templateUrl: './categoria-especifica.component.html',
  styleUrls: ['./categoria-especifica.component.css']
})
export class CategoriaEspecificaComponent {

  categoriaIdInfo: number | null = null;
  idRolUsuario: any;

  loading: boolean = true;
  categoria: categorias_servicios = {
    id: 0,
    tipo: ""
  };

  //permisos que tiene el rol para poder editar 
  permisos: InfoPermiso[] = [];

  constructor(private rolService: RolesService, private categoriaService: CategoriasServicioService, private router: Router,
    private authService: ServicioAuthService) { }

  ngOnInit() {
    this.loading = true
    this.categoriaIdInfo = this.categoriaService.getCategoriaId();
    this.idRolUsuario = this.authService.getIdTipoUsuario();
    if (this.categoriaIdInfo) {
      this.obtenerPermisosSegunRolActual();
      this.obtenerInfoCategoria()
    } else {
      this.router.navigate(['administrador/categorias-registradas']);
      this.loading = false;
    }
  }

  obtenerInfoCategoria() {
    this.categoriaService.obtenerCategoria(this.categoriaIdInfo).subscribe({
      next: (data) => {
        this.categoria = data.categoria
      }, error: (error) => {
        console.log(error)
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

  editarCategoria() {

    if (this.categoria.tipo.length < 1) {
      Swal.fire({
        title: "Debes ingresar un nombre para la categoria",
        icon: "info"
      });
      return
    }

    this.loading = true
    this.categoriaService.actualizarCategoria(this.categoria).subscribe({
      next: (data) => {
        Swal.fire({
          title: data.mensaje,
          icon: 'success'
        })
        this.loading = false
      }, error: (error) => {
        Swal.fire({
          title: error.error.mensaje,
          icon: 'error'
        })
        this.loading = true
        console.log(error)
      }
    })
  }

}
