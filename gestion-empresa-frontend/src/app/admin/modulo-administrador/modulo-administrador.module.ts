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
import { CreacionServicioComponent } from '../modulo-servicios/creacion-servicio/creacion-servicio.component';
import { VistaServiciosComponent } from '../modulo-servicios/vista-servicios/vista-servicios.component';
import { RouterLink, RouterModule } from '@angular/router';
import { VistaServicioEspecificoComponent } from '../modulo-servicios/vistaEspecifica/vista-servicio-especifico/vista-servicio-especifico.component';

@NgModule({
  declarations: [
    PanelAdministradorComponent,
    HeaderAdminComponent,
    InformacionEmpresaComponent,
    VerRolesComponent,
    CrearRolComponent,
    LoadingComponent,
    RolEspecificoComponent,
    CreacionServicioComponent,
    VistaServiciosComponent,
    VistaServicioEspecificoComponent,
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
