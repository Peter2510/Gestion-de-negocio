import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { VistaGeneralComponent } from '../vista-general/vista-general.component';

const routes: Routes = [
  { path: 'vistaGeneral', component: VistaGeneralComponent },
  {
    path: '**',
    redirectTo: 'vistaGeneral',
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UsuariosRoutingModule {}
