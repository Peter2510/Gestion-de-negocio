import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { PanelAdministradorComponent } from '../panel-administrador/panel-administrador.component';
import { InformacionEmpresaComponent } from '../informacion-empresa/informacion-empresa.component';
import { CreacionServicioComponent } from '../modulo-servicios/creacion-servicio/creacion-servicio.component';
import { VistaServiciosComponent } from '../modulo-servicios/vista-servicios/vista-servicios.component';
import { VistaServicioEspecificoComponent } from '../modulo-servicios/vistaEspecifica/vista-servicio-especifico/vista-servicio-especifico.component';
import { VerRolesComponent } from '../modulo-roles/ver-roles/ver-roles.component';
import { CrearRolComponent } from '../modulo-roles/crear-rol/crear-rol.component';
import { RolEspecificoComponent } from '../modulo-roles/rol-especifico/rol-especifico.component';
import { CrearEmpleadoComponent } from '../modulo-empleados/crear-empleado/crear-empleado.component';
import { VerCategoriasComponent } from '../modulo-categorias/ver-categorias/ver-categorias.component';
import { CategoriaEspecificaComponent } from '../modulo-categorias/categoria-especifica/categoria-especifica.component';
import { CrearCategoriaComponent } from '../modulo-categorias/crear-categoria/crear-categoria.component';
import { VerEmpleadosComponent } from '../modulo-empleados/ver-empleados/ver-empleados.component';
import { DetallesUsuarioComponent } from '../modulo-empleados/detalles-usuario/detalles-usuario.component';
import { permissionGuard } from 'src/app/auth/guard/admin/guards.guard';
import { jwtValidoGuard } from 'src/app/auth/guard/admin/jwt-valido.guard';

import { SinAutorizacionComponent } from '../sin-autorizacion/sin-autorizacion.component';
import { PerfilUsuarioComponent } from '../modulo-empleados/perfil-usuario/perfil-usuario.component';

const routes: Routes = [
  {
    path: 'panel-administrador', component: PanelAdministradorComponent,
    canActivate: [jwtValidoGuard],
  },
  {
    path: 'mi-perfil', component: PerfilUsuarioComponent,
    canActivate: [jwtValidoGuard],
  },
  {
    path: 'informacion-empresa', component: InformacionEmpresaComponent,
    canActivate: [permissionGuard, jwtValidoGuard],
    data: { permissions: [2] }
  },
  {
    path: 'roles-registrados', component: VerRolesComponent,
    canActivate: [permissionGuard, jwtValidoGuard],
    data: { permissions: [12] }
  },
  {
    path: 'crear-rol', component: CrearRolComponent,
    canActivate: [permissionGuard, jwtValidoGuard],
    data: { permissions: [3] }
  },
  {
    path: 'detalles-rol', component: RolEspecificoComponent,
    canActivate: [permissionGuard, jwtValidoGuard],
    data: { permissions: [12, 4] }
  },
  {
    path: 'crear-servicio', component: CreacionServicioComponent,
    canActivate: [permissionGuard, jwtValidoGuard],
    data: { permissions: [5] }
  },
  {
    path: 'vista-servicio/:id', component: VistaServicioEspecificoComponent,
    canActivate: [permissionGuard, jwtValidoGuard],
    data: { permissions: [13, 6] }
  },
  {
    path: 'vista-servicio', component: VistaServiciosComponent,
    canActivate: [permissionGuard, jwtValidoGuard],
    data: { permissions: [13] }
  },
  {
    path: 'crear-empleado', component: CrearEmpleadoComponent,
    canActivate: [permissionGuard, jwtValidoGuard],
    data: { permissions: [10] }
  },
  {
    path: 'empleados-registrados', component: VerEmpleadosComponent,
    canActivate: [permissionGuard, jwtValidoGuard],
    data: { permissions: [15] }
  },
  {
    path: 'categorias-registradas', component: VerCategoriasComponent,
    canActivate: [permissionGuard, jwtValidoGuard],
    data: { permissions: [17] }
  },
  {
    path: 'detalles-categoria', component: CategoriaEspecificaComponent,
    canActivate: [permissionGuard, jwtValidoGuard],
    data: { permissions: [17, 18] }
  },
  {
    path: 'crear-categoria', component: CrearCategoriaComponent,
    canActivate: [permissionGuard, jwtValidoGuard],
    data: { permissions: [16] }
  },
  {
    path: 'detalles-usuario', component: DetallesUsuarioComponent,
    canActivate: [permissionGuard, jwtValidoGuard],
    data: { permissions: [15, 11] }
  },
  { path: 'acceso-denegado', component: SinAutorizacionComponent },



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
export class AdministradorRoutingModule { }
