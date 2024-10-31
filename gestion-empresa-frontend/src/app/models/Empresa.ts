export class Empresa {
    id: number;
    nombre: string;
    descripcion: string;
    direccion: string;
    telefono: string;
    email: string;
    logo: string;
    cantidadEmpleados: number;
    cantidadServicios: number;
    tipoServicio: TipoServicio;
    tipoAsignacionCita: TipoAsignacionCita;
}

export class TipoServicio {
    id: number;
    nombre: string;
}

export class TipoAsignacionCita {
    id: number;
    tipo: string;
    activo: boolean;
}