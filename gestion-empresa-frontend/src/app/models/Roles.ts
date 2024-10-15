export interface Rol {
  id: number;
  nombre: string;
  descripcion: string;
}

export interface Permiso {
  id: number;
  nombre: string;
  selected?: boolean; 
}

export interface PermisoRol {
  nombre: string;
  descripcion: string;
  permisos:Permiso[];
}
