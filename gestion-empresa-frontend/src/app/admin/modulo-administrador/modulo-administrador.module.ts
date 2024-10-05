import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PanelAdministradorComponent } from '../panel-administrador/panel-administrador.component';
import { HeaderAdminComponent } from 'src/app/Utils/header-admin/header-admin.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [PanelAdministradorComponent, HeaderAdminComponent],
  imports: [CommonModule, FormsModule],
})
export class ModuloAdministradorModule {}
