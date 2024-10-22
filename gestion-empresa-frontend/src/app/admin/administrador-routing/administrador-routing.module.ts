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
import { SinAutorizacionComponent } from '../sin-autorizacion/sin-autorizacion.component';

const routes: Routes = [
  { path: 'panel-administrador', component: PanelAdministradorComponent, },
  { path: 'informacion-empresa', component: InformacionEmpresaComponent,
    canActivate: [permissionGuard],
    data: { permissions: [2] }
   },
  { path: 'roles-registrados', component: VerRolesComponent,
    canActivate: [permissionGuard],
    data: { permissions: [12] }
   },
  { path: 'crear-rol', component: CrearRolComponent,
    canActivate: [permissionGuard],
    data: { permissions: [3] }
   },
  { path: 'detalles-rol', component: RolEspecificoComponent,
    canActivate: [permissionGuard],
    data: { permissions: [12, 4] }
   },
  { path: 'crear-servicio', component: CreacionServicioComponent,
    canActivate: [permissionGuard],
    data: { permissions: [5] }
   },
  { path: 'vista-servicio/:id', component: VistaServicioEspecificoComponent,
    canActivate: [permissionGuard],
    data: { permissions: [13,6] }
   },
  { path: 'vista-servicio', component: VistaServiciosComponent,
    canActivate: [permissionGuard],
    data: { permissions: [13] }
   },
  { path: 'crear-empleado', component: CrearEmpleadoComponent,
    canActivate: [permissionGuard],
    data: { permissions: [10] }
   },
  { path: 'empleados-registrados', component: VerEmpleadosComponent,
    canActivate: [permissionGuard],
    data: { permissions: [15] }
   },
  { path: 'categorias-registradas', component: VerCategoriasComponent,
    canActivate: [permissionGuard],
    data: { permissions: [17] }
   },
  { path: 'detalles-categoria', component: CategoriaEspecificaComponent,
    canActivate: [permissionGuard],
    data: { permissions: [17, 18] }
   },
  { path: 'crear-categoria', component: CrearCategoriaComponent,
    canActivate: [permissionGuard],
    data: { permissions: [16] }
   },
  { path: 'detalles-usuario', component: DetallesUsuarioComponent,
    canActivate: [permissionGuard],
    data: { permissions: [15,11] }
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
export class AdministradorRoutingModule {}
