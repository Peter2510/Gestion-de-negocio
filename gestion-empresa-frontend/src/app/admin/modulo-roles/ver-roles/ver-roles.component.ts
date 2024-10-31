import { Component, OnInit } from '@angular/core';
import { InfoPermiso, Rol } from 'src/app/models/Roles';
import { RolesService } from '../../services/roles/roles.service';
import { Router } from '@angular/router';
import { ServicioAuthService } from 'src/app/auth/services/servicio-auth.service';

@Component({
  selector: 'app-ver-roles',
  templateUrl: './ver-roles.component.html',
  styleUrls: ['./ver-roles.component.css']
})
export class VerRolesComponent implements OnInit{

  roles:Rol[] = [];
  permisos: InfoPermiso[] = [];
  loading: boolean = true;

  constructor(private rolService:RolesService, private router: Router, private token:ServicioAuthService){

  }
  
  ngOnInit(): void {
    this.obtenerPermisos();
    this.obtenerRolesRegistrados();
  }

  obtenerRolesRegistrados(){
    this.rolService.obtenerRolesRegistrados().subscribe({
      next:(data)=>{
        this.roles = data.roles
        this.loading = false;
      },error:(error)=>{
        this.loading = false;
        console.log(error)
      }
    });
  }

  detallesRol(id: number) {
    this.rolService.setRolId(id);   
    this.router.navigate(['administrador/detalles-rol']);
  }

  obtenerPermisos() {
    this.rolService.obtenerRolYPermisoEspecifico(this.token.getIdTipoUsuario()).subscribe({
      next: (data) => {
        this.permisos = data.permisos
      },
      error: (error) => {
        this.loading = false;
        console.log(error);
      }
    });
  }

  tienePermiso(permisoId: number): boolean {
    return this.permisos.some(permiso => permiso.permisoId === permisoId);
  }



}
