import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderUsuarioComponent } from 'src/app/Utils/header-usuario/header-usuario.component';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';

import { FlatpickrModule } from 'angularx-flatpickr';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { VistaPerfilUsuarioComponent } from '../vista-perfil-usuario/vista-perfil-usuario.component';
import { RouterLink, RouterModule } from '@angular/router';
import { EdicionPerfilComponent } from '../edicion-perfil/edicion-perfil.component';
import { VistaUsuarioComponent } from '../vista-usuario/vista-usuario.component';
@NgModule({
  declarations: [
    HeaderUsuarioComponent,
    VistaPerfilUsuarioComponent,
    EdicionPerfilComponent,
    VistaUsuarioComponent
  ],
  imports: [
    CommonModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    }),
    NgbModalModule,
    FlatpickrModule.forRoot(),
    FormsModule,
    BrowserAnimationsModule,
    RouterLink,
    RouterModule,
  ],
})
export class ModuloUsuariosModule {}
