import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { VistaPerfilUsuarioComponent } from '../vista-perfil-usuario/vista-perfil-usuario.component';
import { EdicionPerfilComponent } from '../edicion-perfil/edicion-perfil.component';
import { VistaUsuarioComponent } from '../vista-usuario/vista-usuario.component';
import { HistorialCitasComponent } from '../historial-citas/historial-citas.component';
import { FacturasComprobantesComponent } from '../facturas-comprobantes/facturas-comprobantes.component';

const routes: Routes = [
  { path: 'vista-usuario', component: VistaUsuarioComponent },
  { path: 'perfil-usuario', component: VistaPerfilUsuarioComponent },
  { path: 'mi-perfil', component: EdicionPerfilComponent },
  { path: 'historial-citas', component: HistorialCitasComponent },
  { path: 'facturas', component: FacturasComprobantesComponent },

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
