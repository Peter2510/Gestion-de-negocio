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
import { CitasServicioService } from 'src/app/usuarios/Services/citas-servicio.service';
import { EventColor } from 'calendar-utils';

const colors: Record<string, EventColor> = {
  red: {
    primary: '#ad2121',
    secondary: '#FAE3E3',
  },
  blue: {
    primary: '#1e90ff',
    secondary: '#D1E8FF',
  },
  yellow: {
    primary: '#e3bc08',
    secondary: '#FDF1BA',
  },
};

@Component({
  selector: 'app-vista-empleado',
  templateUrl: './vista-empleado.component.html',
  styleUrls: ['./vista-empleado.component.css'],
})
export class VistaEmpleadoComponent implements OnInit {
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
  modalOpenVistaEspecifica = false;
  citaEspecificaValor: any;

  openModal() {
    this.modalOpen = true;
  }

  closeModal() {
    this.modalOpen = false;
  }

  /// para los modales especificos
  openModalEspecifico(event: any) {
    this.modalOpenVistaEspecifica = true;
    this.citaEspecificaValor = event;
  }

  closeModalEspecifico() {
    this.modalOpenVistaEspecifica = false;
  }

  saveCita(citaData: any) {
    console.log('Cita guardada:', citaData);
  }

  constructor(private modal: NgbModal) {}

  ngOnInit(): void {
    this.citasServicio.obtenerCitas().subscribe((citas: any) => {
      this.todasCitas = citas.Citas;
      citas.Citas.forEach((element: any) => {
        console.log(element);

        this.events = [
          ...this.events,
          {
            title: element.idServicio.nombre,
            start: new Date(element.fechaHoraInicio),
            end: new Date(element.fechaHoraFin),
            id: element,
            color: colors['blue'],
            draggable: true,
            resizable: {
              beforeStart: true,
              afterEnd: true,
            },
          },
        ];
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
  events: CalendarEvent[] = [
    {
      start: startOfDay('2024-10-25T05:55:58.696Z'), // Fecha de inicio
      end: addDays(new Date(), 1),
      title: 'Evento del día', // Título del evento
      color: { ...colors['red'] },
      draggable: true,
    },
    {
      start: startOfDay(new Date(new Date().setDate(new Date().getDate() + 1))),
      end: addDays(new Date(), 5),
      title: 'Evento de mañana', // Otro evento
      color: { ...colors['yellow'] },
      draggable: true,
    },
  ];

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
    console.log(event);

    this.openModalEspecifico(event);
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
        color: colors['red'],
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
}
