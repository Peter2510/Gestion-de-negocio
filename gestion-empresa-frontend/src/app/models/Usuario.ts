import { Persona } from './Persona';
import { Rol } from './Roles';

export interface Usuario {
  id?: number;
  id_persona?: Persona;
  id_rol?: Rol;
  nombre_usuario: string;
  password: string;
  a2f_activo: boolean;
  activo: boolean;
}
