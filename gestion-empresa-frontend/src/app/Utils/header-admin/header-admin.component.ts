import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { EmpresaService } from 'src/app/admin/services/empresa/empresa.service';
import { PermisosService } from 'src/app/admin/services/permisos/permisos.service';
import { RolesService } from 'src/app/admin/services/roles/roles.service';
import { ServicioAuthService } from 'src/app/auth/services/servicio-auth.service';
import { Empresa } from 'src/app/models/Empresa';
import { Notificacion } from 'src/app/models/Notificacion';
import { InfoPermiso, Permiso } from 'src/app/models/Roles';
import { BuzonService } from 'src/app/services/buzon/buzon.service';

@Component({
  selector: 'app-header-admin',
  templateUrl: './header-admin.component.html',
  styleUrls: ['./header-admin.component.css']
})
export class HeaderAdminComponent implements OnInit {

  empresa: Empresa | null = null; //inicializar como null para manejar el estado de datos no cargados
  loading: boolean = true; //bandera para controlar si los datos están cargando
  permisos: InfoPermiso[] = [];
  tienePermisos = true
  isBusinnesTheme= true
  notificaciones:Notificacion[] = []
  showNotifications = false;

  constructor(private empresaService: EmpresaService, private router: Router, private rolService:RolesService, 
    private token:ServicioAuthService, private buzonService:BuzonService) { }

  ngOnInit(): void {
    this.obtenerPermisos();
    this.obtenerInfoEmpresa();
    this.validarTheme();
    this.obtenerNotificaciones();
  }

  obtenerInfoEmpresa(){
    this.empresaService.obtenerInfoEmpresa().subscribe({
      next: (data) => {
        this.empresa = data.empresa; 
        this.loading = false;
      },
      error: (error) => {
        console.log(error);
        this.loading = false;
      }
    });
  }

  obtenerNotificaciones(){
    console.log(this.token.getIdUsuario());
    
    this.buzonService.obtenerNotificaciones(this.token.getIdUsuario()).subscribe({
      next: (data) => {
        this.notificaciones = data.notificaciones;
        console.log(this.notificaciones);
        console.log(data);
        
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

  get unreadCount(): number {
    return this.notificaciones.filter(n => !n.leido).length;
  }

  validarTheme(){
    const storedTheme = localStorage.getItem('isBusinessTheme');
    this.isBusinnesTheme = storedTheme === 'true';
  }

  toggleTheme(event: Event) {
    this.isBusinnesTheme = (event.target as HTMLInputElement).checked;
    //almaceno el estado en el local storage
    localStorage.setItem('isBusinessTheme', String(this.isBusinnesTheme));
  }

  onSelectChange(event: Event) {
    const target = event.target as HTMLSelectElement;
    const selectedValue = target.value;
  
    if (selectedValue) {
      this.router.navigate([selectedValue]);
    }
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

  tienePermiso(permisoId: number): boolean {
    return this.permisos.some(permiso => permiso.permisoId === permisoId);
  }

  cerrarSesion(){        
    this.token.logout();
  }

  toggleNotifications() {
    this.showNotifications = !this.showNotifications;
  }

  actualizarNotificacion(id:any){
    this.buzonService.actualizarNotificacion(id).subscribe({
      next: (data) => {
        this.obtenerNotificaciones();
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

}