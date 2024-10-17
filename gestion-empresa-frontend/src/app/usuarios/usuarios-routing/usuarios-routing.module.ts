import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { VistaPerfilUsuarioComponent } from '../vista-perfil-usuario/vista-perfil-usuario.component';
import { EdicionPerfilComponent } from '../edicion-perfil/edicion-perfil.component';
import { VistaUsuarioComponent } from '../vista-usuario/vista-usuario.component';

const routes: Routes = [
  { path: 'vista-usuario', component: VistaUsuarioComponent },  
  { path: 'perfil-usuario', component: VistaPerfilUsuarioComponent },
  { path: 'edicion', component: EdicionPerfilComponent },

  {
    path: '**',
    redirectTo: 'vista-usuario',
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UsuariosRoutingModule {}
