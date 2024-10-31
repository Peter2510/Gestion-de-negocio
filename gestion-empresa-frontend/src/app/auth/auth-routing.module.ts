import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegistroComponent } from './registro/registro.component';
import { RecuperacionContraseniaComponent } from './recuperacion-contrasenia/recuperacion-contrasenia.component';
import { Vista2faLoginComponent } from './vista2fa-login/vista2fa-login.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'registro', component: RegistroComponent },
  { path: 'Autenticacion2FA', component: Vista2faLoginComponent },
  { path: 'cambio-contrasenia', component: RecuperacionContraseniaComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AuthRoutingModule {}
