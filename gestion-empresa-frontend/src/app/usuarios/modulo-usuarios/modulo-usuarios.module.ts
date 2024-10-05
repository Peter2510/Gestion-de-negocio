import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VistaGeneralComponent } from '../vista-general/vista-general.component';
import { HeaderUsuarioComponent } from 'src/app/Utils/header-usuario/header-usuario.component';

@NgModule({
  declarations: [VistaGeneralComponent, HeaderUsuarioComponent],
  imports: [CommonModule],
})
export class ModuloUsuariosModule {}
