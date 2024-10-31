import { NgModule } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterLink, RouterModule } from '@angular/router';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { FlatpickrModule } from 'angularx-flatpickr';
import { QRCodeModule } from 'angularx-qrcode';
import { VistaEmpleadoComponent } from '../vista-empleado/vista-empleado.component';
import { PerfilEmpleadoComponent } from '../perfil-empleado/perfil-empleado.component';
import { HeaderEmpleadoComponent } from 'src/app/Utils/header-empleado/header-empleado.component';
import { VistaEspecificaCitasComponent } from '../vista-especifica-citas/vista-especifica-citas.component';

@NgModule({
  declarations: [
    VistaEmpleadoComponent,
    PerfilEmpleadoComponent,
    HeaderEmpleadoComponent,
    VistaEspecificaCitasComponent,
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
    QRCodeModule,
    CurrencyPipe,
  ],
})
export class EmpleadoModuleModule {}
