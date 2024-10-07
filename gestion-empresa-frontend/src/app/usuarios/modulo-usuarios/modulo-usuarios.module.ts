import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VistaGeneralComponent } from '../vista-general/vista-general.component';
import { HeaderUsuarioComponent } from 'src/app/Utils/header-usuario/header-usuario.component';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';

@NgModule({
  declarations: [VistaGeneralComponent, HeaderUsuarioComponent],
  imports: [
    CommonModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    }),
  ],
})
export class ModuloUsuariosModule {}
