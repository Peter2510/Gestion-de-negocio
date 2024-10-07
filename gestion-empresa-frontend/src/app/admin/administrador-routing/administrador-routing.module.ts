import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { PanelAdministradorComponent } from '../panel-administrador/panel-administrador.component';
import { InformacionEmpresaComponent } from '../informacion-empresa/informacion-empresa.component';

const routes: Routes = [
  { path: 'PanelAdministrador', component: PanelAdministradorComponent },
  { path: 'informacionEmpresa', component: InformacionEmpresaComponent },
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
