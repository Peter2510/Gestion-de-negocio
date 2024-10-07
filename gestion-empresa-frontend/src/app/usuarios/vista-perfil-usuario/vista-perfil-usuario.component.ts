import { Component } from '@angular/core';

@Component({
  selector: 'app-vista-perfil-usuario',
  templateUrl: './vista-perfil-usuario.component.html',
  styleUrls: ['./vista-perfil-usuario.component.css'],
})
export class VistaPerfilUsuarioComponent {
  user = {
    name: 'Juan Pérez',
    email: 'juan.perez@example.com',
    role: 'Usuario',
    joined: '2024-01-15',
  };
}
