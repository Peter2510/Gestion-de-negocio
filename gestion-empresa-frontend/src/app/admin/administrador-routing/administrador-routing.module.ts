import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { PanelAdministradorComponent } from '../panel-administrador/panel-administrador.component';

const routes: Routes = [
  { path: 'PanelAdministrador', component: PanelAdministradorComponent },
  {
    path: '**',
    redirectTo: 'PanelAdministrador',
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdministradorRoutingModule {}
