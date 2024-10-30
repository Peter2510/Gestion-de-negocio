import { NgModule, Pipe } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
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
import { LoadingClienteComponent } from '../loading-cliente/loading-cliente.component';
import { ModalCreacionCitaComponent } from '../modal-creacion-cita/modal-creacion-cita.component';
import { HistorialCitasComponent } from '../historial-citas/historial-citas.component';
import { FacturasComprobantesComponent } from '../facturas-comprobantes/facturas-comprobantes.component';
import { QRCodeComponent, QRCodeModule } from 'angularx-qrcode';
import { ModalCitaEspecificaComponent } from '../modal-cita-especifica/modal-cita-especifica.component';
@NgModule({
  declarations: [
    HeaderUsuarioComponent,
    VistaPerfilUsuarioComponent,
    EdicionPerfilComponent,
    VistaUsuarioComponent,
    LoadingClienteComponent,
    ModalCreacionCitaComponent,
    HistorialCitasComponent,
    FacturasComprobantesComponent,
    ModalCitaEspecificaComponent,
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
export class ModuloUsuariosModule {}
