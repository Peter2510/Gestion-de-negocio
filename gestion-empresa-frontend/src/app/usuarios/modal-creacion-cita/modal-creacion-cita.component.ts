import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { ServiciosService } from 'src/app/admin/services/servicios.service';
import { CitasServicioService } from '../Services/citas-servicio.service';

@Component({
  selector: 'app-modal-creacion-cita',
  templateUrl: './modal-creacion-cita.component.html',
  styleUrls: ['./modal-creacion-cita.component.css'],
})
export class ModalCreacionCitaComponent {
  @Input() isOpen: boolean = false;
  @Output() close = new EventEmitter<void>();
  @Output() saveCita = new EventEmitter<any>();

  // ingreos de serivicos
  serviciosService = inject(ServiciosService);
  citasService = inject(CitasServicioService);

  // [ara ver los cambios]
  serviciosEspecificos: any = [];
  seleccionado: any = null;
  horaInicio: any;
  horaFin: any;
  diaLaboral: any;
  idserviciosEspecificos: number = 0;

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

  //formato de conversion
  convertirFecha(fechaISO: string): string {
    if (!fechaISO) {
      console.error('Fecha inválida:', fechaISO);
      return ''; // Manejo de error
    }

    const fecha = new Date(fechaISO);

    // Extraer componentes de la fecha
    const dia = String(fecha.getDate()).padStart(2, '0'); // Asegurar que tenga dos dígitos
    const mes = String(fecha.getMonth() + 1).padStart(2, '0'); // Mes empieza en 0
    const anio = fecha.getFullYear();

    // Extraer componentes de la hora
    const horas = String(fecha.getHours()).padStart(2, '0');
    const minutos = String(fecha.getMinutes()).padStart(2, '0');
    const segundos = String(fecha.getSeconds()).padStart(2, '0'); // Extraer segundos

    // Retornar en el formato deseado
    return `${dia}/${mes}/${anio} ${horas}:${minutos}:${segundos}`; // Cambiado a formato 'dd/MM/yyyy HH:mm:ss'
  }

  crearCita() {
    // Función para convertir el formato de fecha
    console.log(this.horaInicio);

    console.log(this.seleccionado, this.idserviciosEspecificos);
    const valores: number[] = [];
    valores.push(this.idserviciosEspecificos);
    this.citasService
      .crearCitas(
        this.convertirFecha(this.horaInicio),
        this.convertirFecha(this.horaFin),
        28,
        2,
        this.seleccionado,
        valores
      )
      .subscribe();
  }
}
