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

import { PanelAdministradorComponent } from './admin/panel-administrador/panel-administrador.component';
import { ModuloAdministradorModule } from './admin/modulo-administrador/modulo-administrador.module';
import { HeaderUsuarioComponent } from './Utils/header-usuario/header-usuario.component';
import { HeaderAdminComponent } from './Utils/header-admin/header-admin.component';
import { InformacionEmpresaComponent } from './admin/informacion-empresa/informacion-empresa.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { NgbModalModule, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FlatpickrModule } from 'angularx-flatpickr';
import { VistaPerfilUsuarioComponent } from './usuarios/vista-perfil-usuario/vista-perfil-usuario.component';
import { Vista2faLoginComponent } from './auth/vista2fa-login/vista2fa-login.component';
import { RouterLink, RouterModule } from '@angular/router';
import { EdicionPerfilComponent } from './usuarios/edicion-perfil/edicion-perfil.component';
import { ModuloUsuariosModule } from './usuarios/modulo-usuarios/modulo-usuarios.module';



@NgModule({
  declarations: [AppComponent, Vista2faLoginComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ModuloAuthModule,
    ModuloUsuariosModule,
    HttpClientModule,
    NgbModalModule,
    CommonModule,
    FormsModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    }),
    FlatpickrModule.forRoot(),
    RouterLink,
    RouterModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
