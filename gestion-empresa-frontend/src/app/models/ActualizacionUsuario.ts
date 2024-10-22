export class ActualizacionUsuario {
    idUsuario: number;
    nombreUsuario: string;
    idRol: number;
    idGenero: number;
    persona: PersonaActualizacion;
    correo: string;
    activo: boolean;
}

export class PersonaActualizacion{
    cui: number;
    direccion: string;
    nit: string;
    nombre: string;
    telefono: number;
}