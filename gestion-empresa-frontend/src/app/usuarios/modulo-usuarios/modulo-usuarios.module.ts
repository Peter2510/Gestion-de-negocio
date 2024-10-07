import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VistaGeneralComponent } from '../vista-general/vista-general.component';
import { HeaderUsuarioComponent } from 'src/app/Utils/header-usuario/header-usuario.component';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';

import { FlatpickrModule } from 'angularx-flatpickr';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { VistaPerfilUsuarioComponent } from '../vista-perfil-usuario/vista-perfil-usuario.component';
@NgModule({
  declarations: [
    VistaGeneralComponent,
    HeaderUsuarioComponent,
    VistaPerfilUsuarioComponent,
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
  ],
})
export class ModuloUsuariosModule {}
