import { Component, OnInit } from '@angular/core';
import { InfoPermiso } from 'src/app/models/Roles';
import { RolesService } from '../../services/roles/roles.service';
import { Router } from '@angular/router';
import { ServicioAuthService } from 'src/app/auth/services/servicio-auth.service';
import { categorias_servicios } from 'src/app/models/Servicios';
import { CategoriasServicioService } from '../../services/categorias/categorias-servicio.service';

@Component({
  selector: 'app-ver-categorias',
  templateUrl: './ver-categorias.component.html',
  styleUrls: ['./ver-categorias.component.css']
})
export class VerCategoriasComponent implements OnInit{

  permisos: InfoPermiso[] = [];
  categorias: categorias_servicios[] = []
  loading: boolean;


  constructor(private rolService:RolesService, private router: Router, private token:ServicioAuthService,
    private categoriaService:CategoriasServicioService){

  }
  
  ngOnInit(): void {
    this.obtenerPermisos();
    this.obtenerCategorias();
  }


  obtenerPermisos() {
    this.rolService.obtenerRolYPermisoEspecifico(this.token.getIdTipoUsuario()).subscribe({
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

  obtenerCategorias(){
    this.categoriaService.obtenerTodasCategoriasRegistradas().subscribe({
      next: (data)=>{
        this.categorias = data.categorias
        this.loading = false;
      }, error: (error)=>{
        console.log(error)
      }
    })
  }

  tienePermiso(permisoId: number): boolean {
    return this.permisos.some(permiso => permiso.permisoId === permisoId);
  }

  detallesCategoria(id: number) {
    this.categoriaService.seCategoriaId(id);   
    this.router.navigate(['administrador/detalles-categoria']);
    
  }


}
