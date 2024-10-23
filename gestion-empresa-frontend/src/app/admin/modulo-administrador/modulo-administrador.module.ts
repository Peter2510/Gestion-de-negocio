import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PanelAdministradorComponent } from '../panel-administrador/panel-administrador.component';
import { HeaderAdminComponent } from 'src/app/Utils/header-admin/header-admin.component';
import { FormsModule } from '@angular/forms';
import { InformacionEmpresaComponent } from '../informacion-empresa/informacion-empresa.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FullCalendarModule } from '@fullcalendar/angular';
import { VerRolesComponent } from '../modulo-roles/ver-roles/ver-roles.component';
import { CrearRolComponent } from '../modulo-roles/crear-rol/crear-rol.component';
import { LoadingComponent } from '../loading/loading.component';
import { RolEspecificoComponent } from '../modulo-roles/rol-especifico/rol-especifico.component';
import { CrearEmpleadoComponent } from '../modulo-empleados/crear-empleado/crear-empleado.component';
import { CreacionServicioComponent } from '../modulo-servicios/creacion-servicio/creacion-servicio.component';
import { VistaServiciosComponent } from '../modulo-servicios/vista-servicios/vista-servicios.component';
import { VistaServicioEspecificoComponent } from '../modulo-servicios/vistaEspecifica/vista-servicio-especifico/vista-servicio-especifico.component';
import { RouterLink, RouterModule } from '@angular/router';
import { VerCategoriasComponent } from '../modulo-categorias/ver-categorias/ver-categorias.component';
import { CategoriaEspecificaComponent } from '../modulo-categorias/categoria-especifica/categoria-especifica.component';
import { CrearCategoriaComponent } from '../modulo-categorias/crear-categoria/crear-categoria.component';
import { VerEmpleadosComponent } from '../modulo-empleados/ver-empleados/ver-empleados.component';
import { DetallesUsuarioComponent } from '../modulo-empleados/detalles-usuario/detalles-usuario.component';
import { SinAutorizacionComponent } from '../sin-autorizacion/sin-autorizacion.component';
import { PerfilUsuarioComponent } from '../modulo-empleados/perfil-usuario/perfil-usuario.component';



@NgModule({
  declarations: [
     PanelAdministradorComponent,
     HeaderAdminComponent,
     InformacionEmpresaComponent,
     VerRolesComponent,
     CrearRolComponent, 
     LoadingComponent,
     RolEspecificoComponent,
     CrearEmpleadoComponent,
     CreacionServicioComponent,
    VistaServiciosComponent,
    VistaServicioEspecificoComponent,
    VerCategoriasComponent,
    CategoriaEspecificaComponent,
    CrearCategoriaComponent,
    VerEmpleadosComponent,
    DetallesUsuarioComponent,
    SinAutorizacionComponent,
    PerfilUsuarioComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterLink,
    RouterModule,
    FullCalendarModule,
    BrowserAnimationsModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    }),
    NgbModule,
  ],
})
export class ModuloAdministradorModule {}
