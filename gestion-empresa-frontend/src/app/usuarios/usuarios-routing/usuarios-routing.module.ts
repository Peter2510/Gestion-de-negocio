import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { VistaGeneralComponent } from '../vista-general/vista-general.component';
import { VistaPerfilUsuarioComponent } from '../vista-perfil-usuario/vista-perfil-usuario.component';
import { EdicionPerfilComponent } from '../edicion-perfil/edicion-perfil.component';

const routes: Routes = [
  
  { path: 'perfil-usuario', component: VistaPerfilUsuarioComponent },
  { path: 'edicion', component: EdicionPerfilComponent },
  { path: 'vista-general', component: VistaGeneralComponent },
  {
    path: '**',
    redirectTo: 'vista-general',
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UsuariosRoutingModule {}
