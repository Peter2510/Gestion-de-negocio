import { Component, OnInit } from '@angular/core';
import { InfoPermiso } from 'src/app/models/Roles';
import { Usuario } from 'src/app/models/Usuario';
import { RolesService } from '../../services/roles/roles.service';
import { Router } from '@angular/router';
import { ServicioAuthService } from 'src/app/auth/services/servicio-auth.service';
import { UsuarioService } from '../../services/usuario/usuario.service';

@Component({
  selector: 'app-ver-empleados',
  templateUrl: './ver-empleados.component.html',
  styleUrls: ['./ver-empleados.component.css']
})
export class VerEmpleadosComponent implements OnInit{

  empleados: Usuario[] = [];
  permisos: InfoPermiso[] = [];
  loading: boolean = true;

  constructor(private rolService:RolesService, private router: Router, private token:ServicioAuthService,
    private empleadosService:UsuarioService){}

  ngOnInit(): void {
    this.obtenerPermisos();
    this.obtenerEmpleados();
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

  obtenerEmpleados(){
    this.empleadosService.obtenerEmpleados().subscribe({
      next: (data)=>{
        this.empleados = data.empleados
        this.loading = false;
      }, error: (error)=>{
        this.loading = false;
        console.log(error)
      }
    })
  }

  tienePermiso(permisoId: number): boolean {
    return this.permisos.some(permiso => permiso.permisoId === permisoId);
  }

  detallesEmpleado(id: number) {
    this.empleadosService.setIdUsuario(id);   
    this.router.navigate(['administrador/detalles-usuario']);
  }
}
