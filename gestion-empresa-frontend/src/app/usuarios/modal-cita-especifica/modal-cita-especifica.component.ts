import {
  Component,
  EventEmitter,
  inject,
  Input,
  OnInit,
  Output,
} from '@angular/core';
import { ServiciosService } from 'src/app/admin/services/servicios.service';
import { CitasServicioService } from '../Services/citas-servicio.service';

@Component({
  selector: 'app-modal-cita-especifica',
  templateUrl: './modal-cita-especifica.component.html',
  styleUrls: ['./modal-cita-especifica.component.css'],
})
export class ModalCitaEspecificaComponent implements OnInit {
  @Input() isOpen: boolean = false;
  @Input() valor: any;
  @Output() close = new EventEmitter<void>();
  @Output() saveCita = new EventEmitter<any>();

  // ingreos de serivicos
  serviciosService = inject(ServiciosService);
  citasService = inject(CitasServicioService);

  // [ara ver los cambios]
  serviciosEspecificos: any = [];
  seleccionado: any = null;
  seleccionadoDuracion: any = null;
  horaInicio: any;
  horaFin: any;
  diaLaboral: any;
  idserviciosEspecificos: any = 0;
  cantidadServicios: number = 0;
  duracionServicio: any = '';

  cerrarModal() {
    this.close.emit();
    this.isOpen = false;
  }

  // para que seleccione algo determinado en el cambio
  onSelectChange(event: Event) {
    const target = event.target as HTMLSelectElement;
    this.seleccionado = target.value;
    console.log(this.seleccionado);
    this.serviciosService
      .obtenerTodosServiciosEspecificos(this.seleccionado)
      .subscribe((servicios: any) => {
        console.log(servicios);

        this.serviciosEspecificos = servicios.todoServiciosEspecificos;
      });
  }

  // cambio para ver el tipo de duracion
  onSelectChange2(event: Event) {
    const target = event.target as HTMLSelectElement;
    this.seleccionadoDuracion = this.idserviciosEspecificos;
    console.log(this.seleccionadoDuracion[0], this.idserviciosEspecificos);
    this.serviciosService
      .obtenerDuracionServicio(this.idserviciosEspecificos)
      .subscribe((servicios: any) => {
        console.log(servicios);

        this.duracionServicio = servicios.duracion;
      });
  }

  ngOnInit(): void {
    console.log(this.valor);
  }
}
