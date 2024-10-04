export interface Persona {
  id: number;
  cui: number;
  direccion: string;
  nit: string;
  nombre: string;
  numero: number;
  telefono: number;
  genero: Genero;
}

// para los generos

export interface Genero {
  id: number;
  nombre: string;
}
