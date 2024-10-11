import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { PanelAdministradorComponent } from '../panel-administrador/panel-administrador.component';
import { InformacionEmpresaComponent } from '../informacion-empresa/informacion-empresa.component';
import { VerRolesComponent } from '../modulo-roles/ver-roles/ver-roles.component';

const routes: Routes = [
  { path: 'panel-administrador', component: PanelAdministradorComponent },
  { path: 'informacion-empresa', component: InformacionEmpresaComponent },
  { path: 'roles-registrados', component: VerRolesComponent },
  {
    path: '**',
    redirectTo: 'PanelAdministrador',
    pathMatch: 'full',
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdministradorRoutingModule {}
