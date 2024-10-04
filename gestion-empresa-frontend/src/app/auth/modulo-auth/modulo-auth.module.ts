import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { LoginComponent } from '../login/login.component';
import { RecuperacionContraseniaComponent } from '../recuperacion-contrasenia/recuperacion-contrasenia.component';
import { RegistroComponent } from '../registro/registro.component';

@NgModule({
  declarations: [
    LoginComponent,
    RegistroComponent,
    RecuperacionContraseniaComponent,
  ],
  imports: [CommonModule, RouterLink],
})
export class ModuloAuthModule {}
