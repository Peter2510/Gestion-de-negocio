import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';

const routes: Routes = [
  {
    path: 'auth',
    loadChildren: () =>
      import('./auth/auth-routing.module').then((m) => m.AuthRoutingModule),
  },
  {
    path: 'usuarios',
    loadChildren: () =>
      import('./usuarios/usuarios-routing/usuarios-routing.module').then(
        (m) => m.UsuariosRoutingModule
      ),
  },
  {
    path: 'administrador',
    loadChildren: () =>
      import('./admin/administrador-routing/administrador-routing.module').then(
        (m) => m.AdministradorRoutingModule
      ),
  },
  {
    path: '**',
    redirectTo: '/auth/login',
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
