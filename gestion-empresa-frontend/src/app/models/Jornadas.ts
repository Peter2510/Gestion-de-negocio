import { Time } from '@angular/common';

export interface jornada_laboral {
  id?: number;
  hora_fin: Time;
  hora_inicio: Time;
  nombre: string;
}

export interface dias_laborales {
  id?: number;
  nombre: string;
}

export interface JornadaPorDia {
  id?: number;
  jornada_laboral: jornada_laboral;
  dias_laborales: dias_laborales;
}
