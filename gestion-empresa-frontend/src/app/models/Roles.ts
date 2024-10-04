export interface Rol {
  id: number;
  tipo: string;
}

export interface Permisos {
  id: number;
  nombre: string;
}

export interface PermisosRol {
  id: number;
  idRol: Rol;
  idPermiso: Permisos;
}
