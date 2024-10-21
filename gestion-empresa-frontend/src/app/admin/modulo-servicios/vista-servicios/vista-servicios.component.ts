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
      estadoServicio: { id: 1, estado: 'Activo' },
      categoria: { id: 1, tipo: 'Hogar' },
    },
    {
      id: 2,
      nombre: 'Servicio de Limpieza',
      descripcion: 'Descripción del servicio de limpieza.',
      imagen: 'ruta/a/imagen.jpg',
      estadoServicio: { id: 1, estado: 'Activo' },
      categoria: { id: 1, tipo: 'Hogar' },
    },
    {
      id: 3,
      nombre: 'Servicio de Limpieza',
      descripcion: 'Descripción del servicio de limpieza.',
      imagen: 'ruta/a/imagen.jpg',
      estadoServicio: { id: 1, estado: 'Activo' },
      categoria: { id: 1, tipo: 'Hogar' },
    },
    // Más servicios...
  ];

  serviciosPrestados: serviciosPrestados[] = [
    {
      id: 1,
      nombre: 'Limpieza de Oficina',
      precio: 0,
      estadoServicio: { id: 2, estado: 'Completado' },
    },
    // Más servicios prestados...
  ];
}
