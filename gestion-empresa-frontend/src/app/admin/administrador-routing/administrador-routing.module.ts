import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { PanelAdministradorComponent } from '../panel-administrador/panel-administrador.component';
import { InformacionEmpresaComponent } from '../informacion-empresa/informacion-empresa.component';
import { VerRolesComponent } from '../modulo-roles/ver-roles/ver-roles.component';
import { CrearRolComponent } from '../modulo-roles/crear-rol/crear-rol.component';
import { RolEspecificoComponent } from '../modulo-roles/rol-especifico/rol-especifico.component';
import { CreacionServicioComponent } from '../modulo-servicios/creacion-servicio/creacion-servicio.component';
import { VistaServiciosComponent } from '../modulo-servicios/vista-servicios/vista-servicios.component';
import { VistaServicioEspecificoComponent } from '../modulo-servicios/vistaEspecifica/vista-servicio-especifico/vista-servicio-especifico.component';

const routes: Routes = [
  { path: 'panel-administrador', component: PanelAdministradorComponent },
  { path: 'informacion-empresa', component: InformacionEmpresaComponent },
  { path: 'roles-registrados', component: VerRolesComponent },
  { path: 'crear-rol', component: CrearRolComponent },
  { path: 'detalles-rol', component: RolEspecificoComponent },
  { path: 'crear-servicio', component: CreacionServicioComponent },
  { path: 'vista-servicio/:id', component: VistaServicioEspecificoComponent },
  { path: 'vista-servicio', component: VistaServiciosComponent },
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
