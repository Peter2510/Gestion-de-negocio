import { Time } from '@angular/common';

export interface servicios {
  id?: number;
  nombre: string;
  descripcion: string;
  imagen: string;
  estadoServicio: estadoServicio;
  categoria: categorias_servicios;
}

export interface serviciosPrestados {
  id?: number;
  precio: number;
  nombre: string;
  estadoServicio: estadoServicio;
}

export interface estadoServicio {
  id?: number;
  estado: string;
}

export interface categorias_servicios {
  id: number;
  tipo: string;
}

export interface DuracionServicioPrestado {
  id?: number;
  nombre: string;
  duracion: Time;
  servicioPrestado: serviciosPrestados;
}
