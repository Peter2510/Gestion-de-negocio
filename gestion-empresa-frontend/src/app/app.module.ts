import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './auth/login/login.component';
import { RegistroComponent } from './auth/registro/registro.component';
import { RecuperacionContraseniaComponent } from './auth/recuperacion-contrasenia/recuperacion-contrasenia.component';
import { ModuloAuthModule } from './auth/modulo-auth/modulo-auth.module';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { VistaGeneralComponent } from './usuarios/vista-general/vista-general.component';
import { ModuloUsuariosModule } from './usuarios/modulo-usuarios/modulo-usuarios.module';
import { PanelAdministradorComponent } from './admin/panel-administrador/panel-administrador.component';
import { ModuloAdministradorModule } from './admin/modulo-administrador/modulo-administrador.module';
import { HeaderUsuarioComponent } from './Utils/header-usuario/header-usuario.component';
import { HeaderAdminComponent } from './Utils/header-admin/header-admin.component';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ModuloAuthModule,
    ModuloUsuariosModule,
    HttpClientModule,
    CommonModule,
    FormsModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    }),
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
