export class Empresa {
    id: number;
    nombre: string;
    direccion: string;
    telefono: string;
    email: string;
    logo: string;
    cantidadEmpleados: number;
    cantidadServicios: number;
    TipoServicio: TipoServicio;
    TipoAsignacionCita: tipoAsignacionCita;
}

export class TipoServicio {
    id: number;
    nombre: string;
}

export class tipoAsignacionCita {
    id: number;
    nombre: string;
    activo: boolean;
}