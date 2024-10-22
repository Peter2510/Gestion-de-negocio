import { Component, inject } from '@angular/core';
import { servicios, serviciosPrestados } from 'src/app/models/Servicios';
import { ServiciosService } from '../../services/servicios.service';

@Component({
  selector: 'app-vista-servicios',
  templateUrl: './vista-servicios.component.html',
  styleUrls: ['./vista-servicios.component.css'],
})
export class VistaServiciosComponent {
  serviciosServicio = inject(ServiciosService);
}
