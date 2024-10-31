import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VistaEmpleadoComponent } from '../vista-empleado/vista-empleado.component';
import { Routes, RouterModule } from '@angular/router';
import { PerfilUsuarioComponent } from 'src/app/admin/modulo-empleados/perfil-usuario/perfil-usuario.component';
import { SinAutorizacionComponent } from 'src/app/admin/sin-autorizacion/sin-autorizacion.component';
import { jwtValidoGuard } from 'src/app/auth/guard/admin/jwt-valido.guard';
import { VistaEspecificaCitasComponent } from '../vista-especifica-citas/vista-especifica-citas.component';

const routes: Routes = [
  {
    path: 'panel-empleado',
    component: VistaEmpleadoComponent,
    canActivate: [jwtValidoGuard],
  },
  {
    path: 'mi-perfil',
    component: PerfilUsuarioComponent,
    canActivate: [jwtValidoGuard],
  },
  {
    path: 'vistaEspecifica',
    component: VistaEspecificaCitasComponent,
    canActivate: [jwtValidoGuard],
  },

  { path: 'acceso-denegado', component: SinAutorizacionComponent },

  {
    path: '**',
    redirectTo: 'panel-empleado',
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RoutingEmpeladoModuleModule {}
