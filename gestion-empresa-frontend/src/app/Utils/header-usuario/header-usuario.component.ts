import { Component, OnInit } from '@angular/core';
import { Empresa } from 'src/app/models/Empresa';
import { EmpresaService } from 'src/app/admin/services/empresa/empresa.service';
import { ServicioAuthService } from 'src/app/auth/services/servicio-auth.service';
import { Notificacion } from 'src/app/models/Notificacion';
import { BuzonService } from 'src/app/services/buzon/buzon.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header-usuario',
  templateUrl: './header-usuario.component.html',
  styleUrls: ['./header-usuario.component.css'],
})
export class HeaderUsuarioComponent implements OnInit {
  empresa: Empresa | null = null; //inicializar como null para manejar el estado de datos no cargados
  loading: boolean = true; //bandera para controlar si los datos están cargando
  isBusinnesTheme= true
  notificaciones:Notificacion[] = []
  showNotifications = false;

  constructor(private empresaService: EmpresaService, private token:ServicioAuthService, 
    private buzonService:BuzonService, private router:Router) {}

  ngOnInit(): void {
    this.obtenerInfoEmpresa();
    this.validarTheme();
  }

  onSelectChange(event: Event) {
    const target = event.target as HTMLSelectElement;
    const selectedValue = target.value;
  
    if (selectedValue) {
      this.router.navigate([selectedValue]);
    }
  }

  obtenerInfoEmpresa(){
    this.empresaService.obtenerInfoEmpresa().subscribe({
      next: (data) => {
        this.empresa = data.empresa;
        console.log(this.empresa)
        console.log(this.loading)
        this.loading = false; //desactivar la bandera de carga cuando los datos estén listos
        console.log(this.loading)
      },
      error: (error) => {
        console.log(error);
        this.loading = false;
      },
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

  validarTheme(){
    const storedTheme = localStorage.getItem('isBusinessTheme');
    this.isBusinnesTheme = storedTheme === 'true';
  }

  toggleTheme(event: Event) {
    this.isBusinnesTheme = (event.target as HTMLInputElement).checked;
    //almaceno el estado en el local storage
    localStorage.setItem('isBusinessTheme', String(this.isBusinnesTheme));
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

  get unreadCount(): number {
    return this.notificaciones.filter(n => !n.leido).length;
  }


}
