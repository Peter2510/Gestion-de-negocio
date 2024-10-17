import { Time } from '@angular/common';

export interface servicios {
  id?: number;
  nombre: string;
  descripcion: string;
  imagen: string;
  estadoSerivicio: estadoServicio;
  categoria: categorias_servicios;
}

export interface serviciosPrestados {
  id?: number;
  estado: string;
  nombre: string;
  estadoSerivicio: estadoServicio;
  duracionServicioPrestado: DuracionServicioPrestado;
}

export interface estadoServicio {
  id?: number;
  estado: string;
}

export interface categorias_servicios {
  id?: number;
  tipo: string;
}

export interface DuracionServicioPrestado {
  id?: number;
  nombre: string;
  duracion: Time;
}
