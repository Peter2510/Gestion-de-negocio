import { Component } from '@angular/core';
import { servicios, serviciosPrestados } from 'src/app/models/Servicios';

@Component({
  selector: 'app-vista-servicios',
  templateUrl: './vista-servicios.component.html',
  styleUrls: ['./vista-servicios.component.css'],
})
export class VistaServiciosComponent {
  servicios: servicios[] = [
    {
      id: 1,
      nombre: 'Servicio de Limpieza',
      descripcion: 'Descripción del servicio de limpieza.',
      imagen: 'ruta/a/imagen.jpg',
      estadoSerivicio: { id: 1, estado: 'Activo' },
      categoria: { id: 1, tipo: 'Hogar' },
    },
    {
      id: 2,
      nombre: 'Servicio de Limpieza',
      descripcion: 'Descripción del servicio de limpieza.',
      imagen: 'ruta/a/imagen.jpg',
      estadoSerivicio: { id: 1, estado: 'Activo' },
      categoria: { id: 1, tipo: 'Hogar' },
    },
    {
      id: 3,
      nombre: 'Servicio de Limpieza',
      descripcion: 'Descripción del servicio de limpieza.',
      imagen: 'ruta/a/imagen.jpg',
      estadoSerivicio: { id: 1, estado: 'Activo' },
      categoria: { id: 1, tipo: 'Hogar' },
    },
    // Más servicios...
  ];

  serviciosPrestados: serviciosPrestados[] = [
    {
      id: 1,
      nombre: 'Limpieza de Oficina',
      estado: 'Finalizado',
      estadoSerivicio: { id: 2, estado: 'Completado' },
      duracionServicioPrestado: {
        id: 1,
        nombre: 'Duración corta',
        duracion: { hours: 1, minutes: 30 },
      },
    },
    // Más servicios prestados...
  ];
}
