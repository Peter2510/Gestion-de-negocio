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

@NgModule({
  declarations: [PanelAdministradorComponent,
     HeaderAdminComponent,
     InformacionEmpresaComponent
  ],
  imports: [CommonModule, FormsModule,
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