import { Component, TemplateRef, ViewChild } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CalendarEvent, CalendarEventAction, CalendarEventTimesChangedEvent, CalendarView } from 'angular-calendar';
import { addDays, endOfDay, isSameDay, isSameMonth, startOfDay } from 'date-fns';
import { Subject } from 'rxjs';
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
  selector: 'app-vista-usuario',
  templateUrl: './vista-usuario.component.html',
  styleUrls: ['./vista-usuario.component.css']
})

export class VistaUsuarioComponent {
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
  constructor(private modal: NgbModal) {}

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
      start: startOfDay(new Date()), // Fecha de inicio
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
    this.modalData = { event, action };
  }

  isModalOpen = false;

  openModal(event: any) {
    this.selectedEvent = event;
    this.eventDate = this.formatDate(event.start);
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;
    this.modal.dismissAll();
  }

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