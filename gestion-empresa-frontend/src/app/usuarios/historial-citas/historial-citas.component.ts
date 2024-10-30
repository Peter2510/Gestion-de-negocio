import {
  Component,
  inject,
  OnInit,
  TemplateRef,
  ViewChild,
} from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {
  CalendarView,
  CalendarEvent,
  CalendarEventTimesChangedEvent,
  CalendarEventAction,
} from 'angular-calendar';
import {
  isSameMonth,
  isSameDay,
  startOfDay,
  addDays,
  endOfDay,
} from 'date-fns';
import { Subject } from 'rxjs';
import { CitasServicioService } from '../Services/citas-servicio.service';

import { EventColor } from 'calendar-utils';

const colors: Record<string, EventColor> = {
  yellow: {
    primary: '#e3bc08',
    secondary: '#FDF1BA',
  },
};
@Component({
  selector: 'app-historial-citas',
  templateUrl: './historial-citas.component.html',
  styleUrls: ['./historial-citas.component.css'],
})
export class HistorialCitasComponent implements OnInit {
  @ViewChild('modalContent', { static: true }) modalContent: TemplateRef<any>;
  view: CalendarView = CalendarView.Month; // Cambia la vista: Month, Week o Day
  viewDate: Date = new Date(); // Fecha actual
  CalendarView = CalendarView;

  activeDayIsOpen: boolean = true;

  refresh = new Subject<void>();

  modalData: {
    action: string;
    event: CalendarEvent;
  };
  selectedDate: any;

  //servicios
  citasServicio = inject(CitasServicioService);
  todasCitas: any;

  // para el modal
  modalOpen = false;

  openModal() {
    this.modalOpen = true;
  }

  closeModal() {
    this.modalOpen = false;
  }

  saveCita(citaData: any) {
    console.log('Cita guardada:', citaData);
  }

  constructor(private modal: NgbModal) {}

  ngOnInit(): void {
    this.citasServicio.obtenerCitasId().subscribe((citas: any) => {
      this.todasCitas = citas.todoServiciosEspecificos;
      citas.todoServiciosEspecificos.forEach((element: any) => {
        console.log(element);

        //agrega los eventos de valor
        this.events = [
          ...this.events,
          {
            title: element.idServicio.nombre,
            start: new Date(element.fechaHoraInicio),
            end: new Date(element.fechaHoraFin),
            color: colors['yellow'],
            draggable: true,
            resizable: {
              beforeStart: true,
              afterEnd: true,
            },
          },
        ];

        // ahora determina los precios
      });
      console.log(this.events);
    });
  }

  dayClicked({ date, events }: { date: Date; events: CalendarEvent[] }): void {
    if (isSameMonth(date, this.viewDate)) {
      if (
        (isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) ||
        events.length === 0
      ) {
        this.activeDayIsOpen = false;
      } else {
        this.activeDayIsOpen = true;
      }
      this.viewDate = date;
    }
  }

  eventTimesChanged({
    event,
    newStart,
    newEnd,
  }: CalendarEventTimesChangedEvent): void {
    this.events = this.events.map((iEvent) => {
      if (iEvent === event) {
        return {
          ...event,
          start: newStart,
          end: newEnd,
        };
      }
      return iEvent;
    });
    this.handleEvent('Dropped or resized', event);
  }
  // Eventos del calendario
  events: CalendarEvent[] = [];

  actions: CalendarEventAction[] = [
    {
      label: '<i class="fas fa-fw fa-pencil-alt"></i>',
      a11yLabel: 'Edit',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        this.handleEvent('Edited', event);
      },
    },
    {
      label: '<i class="fas fa-fw fa-trash-alt"></i>',
      a11yLabel: 'Delete',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        this.events = this.events.filter((iEvent) => iEvent !== event);
        this.handleEvent('Deleted', event);
      },
    },
  ];

  selectedEvent: CalendarEvent | null = null;
  eventDate: string = ''; // Variable para almacenar la fecha de edición

  // Métodos para cambiar la vista del calendario
  setView(view: CalendarView) {
    this.view = view;
  }

  // Método para manejar el clic en un evento

  handleEvent(action: string, event: CalendarEvent): void {
    this.modalData = { event, action };
  }

  isModalOpen = false;

  saveEvent(): void {
    console.log('Evento actualizado:', this.selectedEvent);
    this.isModalOpen = false;
  }

  addEvent(): void {
    this.events = [
      ...this.events,
      {
        title: 'New event',
        start: startOfDay(new Date()),
        end: endOfDay(new Date()),
        color: colors['yellow'],
        draggable: true,
        resizable: {
          beforeStart: true,
          afterEnd: true,
        },
      },
    ];
    console.log(this.events);
  }

  cancelEdit(): void {
    this.selectedEvent = null; // Cierra el modal
  }

  deleteEvent(eventToDelete: CalendarEvent) {
    this.events = this.events.filter((event) => event !== eventToDelete);
  }

  // Formatear la fecha en formato YYYY-MM-DD para el input
  formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2);
    const day = ('0' + date.getDate()).slice(-2);
    return `${year}-${month}-${day}`;
  }

  closeOpenMonthViewDay() {
    this.activeDayIsOpen = false;
  }

  //******** */ Paginación
  currentPage = 1;
  itemsPerPage = 5;

  get totalPages(): number {
    return Math.ceil(this.todasCitas.length / this.itemsPerPage);
  }

  get paginatedItems() {
    const start = (this.currentPage - 1) * this.itemsPerPage;
    return this.todasCitas.slice(start, start + this.itemsPerPage);
  }

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
    }
  }

  prevPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }
}
