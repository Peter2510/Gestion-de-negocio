import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PanelAdministradorComponent } from '../panel-administrador/panel-administrador.component';
import { HeaderAdminComponent } from 'src/app/Utils/header-admin/header-admin.component';
import { FormsModule } from '@angular/forms';
import { InformacionEmpresaComponent } from '../informacion-empresa/informacion-empresa.component';

@NgModule({
  declarations: [PanelAdministradorComponent,
     HeaderAdminComponent,
     InformacionEmpresaComponent
  ],
  imports: [CommonModule, FormsModule],
})
export class ModuloAdministradorModule {}
