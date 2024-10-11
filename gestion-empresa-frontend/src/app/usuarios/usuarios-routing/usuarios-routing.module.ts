import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { VistaGeneralComponent } from '../vista-general/vista-general.component';
import { VistaPerfilUsuarioComponent } from '../vista-perfil-usuario/vista-perfil-usuario.component';
import { EdicionPerfilComponent } from '../edicion-perfil/edicion-perfil.component';

const routes: Routes = [
  { path: 'vistaGeneral', component: VistaGeneralComponent },
  { path: 'PerfilUsuario', component: VistaPerfilUsuarioComponent },
  { path: 'Edicion', component: EdicionPerfilComponent },
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