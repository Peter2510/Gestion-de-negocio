import { Persona } from './Persona';
import { Rol } from './Roles';

export interface Usuario {
  id: number;
  persona: Persona;
  rol: Rol;
  nombreUsuario: string;
  password: string;
  a2f_activo: boolean;
  activo: boolean;
}
