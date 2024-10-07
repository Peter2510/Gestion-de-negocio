import { AfterViewInit, Component, ElementRef, OnInit } from '@angular/core';
import { CalendarView, CalendarEvent } from 'angular-calendar';
import { startOfDay } from 'date-fns';
import { HeaderUsuarioComponent } from 'src/app/Utils/header-usuario/header-usuario.component';

@Component({
  selector: 'app-vista-general',
  templateUrl: './vista-general.component.html',
  styleUrls: ['./vista-general.component.css'],
})
export class VistaGeneralComponent {
  view: CalendarView = CalendarView.Month; // Cambia la vista: Month, Week o Day
  viewDate: Date = new Date(); // Fecha actual
  CalendarView = CalendarView;

  // Eventos del calendario
  events: CalendarEvent[] = [
    {
      start: startOfDay(new Date()), // Fecha de inicio
      title: 'Evento del día', // Título del evento
    },
  ];

  // Métodos para cambiar la vista del calendario
  setView(view: CalendarView) {
    this.view = view;
  }

  closeOpenMonthViewDay() {
    // Método para cerrar el día seleccionado en la vista de mes
  }
}
