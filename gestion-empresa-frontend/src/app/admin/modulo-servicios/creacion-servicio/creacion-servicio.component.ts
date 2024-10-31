import { Component, inject, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CategoriasServicioService } from '../../services/categorias/categorias-servicio.service';
import { dias_laborales, jornada_laboral } from 'src/app/models/Jornadas';
import { Time } from '@angular/common';
import {
  DuracionServicioPrestado,
  estadoServicio,
  servicios,
  serviciosPrestados,
} from 'src/app/models/Servicios';
import { JornadasLaboralesServicioService } from '../../services/Jornadas/jornadas-laborales-servicio.service';
import { ServiciosService } from '../../services/servicios.service';
import { HttpStatusCode } from '@angular/common/http';
import Swal from 'sweetalert2';
import { EmpresaService } from '../../services/empresa/empresa.service';
import { UsuarioService } from '../../services/usuario/usuario.service';
import { Usuario } from 'src/app/models/Usuario';
import { Empresa } from 'src/app/models/Empresa';

@Component({
  selector: 'app-creacion-servicio',
  templateUrl: './creacion-servicio.component.html',
  styleUrls: ['./creacion-servicio.component.css'],
})
export class CreacionServicioComponent implements OnInit {
  servicioForm: FormGroup;
  arregloHorarios: any = [];
  diasParaHorario: number[] = [];
  servicioGenral: any = [];
  usuarioSeleccionado: number = 0;
  arregloServiciosEspecificos: serviciosPrestados[] = [];
  arregloDuracionServiciosEspecificos: DuracionServicioPrestado[] = [];
  todasCategorias: any;
  currentStep = 1;
  // valores para las jornadas
  horaInicio!: Time;
  horaFin!: Time;
  nombreJornada!: string;
  id_dia_laboral: number[] = [];

  //servicio
  categoriaEstadoServicio = inject(CategoriasServicioService);
  jornadasServicio = inject(JornadasLaboralesServicioService);
  empresaServicio = inject(EmpresaService);
  usuariosServicio = inject(UsuarioService);

  servicioServicio = inject(ServiciosService);
  nombreServicio: any;
  descripcionServicio: any;
  estadoSerivicioServicio: any;
  categoriaServicio: any;

  //valor de la empresa
  empresa: Empresa = {
    id: 0,
    nombre: '',
    descripcion: '',
    direccion: '',
    telefono: '',
    email: '',
    logo: '',
    cantidadEmpleados: 0,
    cantidadServicios: 0,
    tipoServicio: {
      id: 0,
      nombre: '',
    },
    tipoAsignacionCita: {
      id: 0,
      tipo: '',
      activo: false,
    },
  };
  todosUsuarios: Usuario[] = [];
  // movimientos para el steper
  goToStep(step: number) {
    if (this.currentStep === 1) {
      this.currentStep = step;
    } else if (this.currentStep === 2) {
      this.currentStep = step;
    } else if (this.currentStep === 3) {
      this.currentStep = step;
    }
  }

  selectedImage: string | ArrayBuffer | null = null;

  onImageSelected(event: Event) {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        this.selectedImage = e.target?.result as string | ArrayBuffer; // Afirmación de tipo
      };
      reader.readAsDataURL(file);
    }
  }

  // para agregar mas
  agregarHorario() {
    const nuevoHorario: jornada_laboral = {
      id: this.arregloHorarios.length,
      hora_fin: this.horaFin,
      hora_inicio: this.horaInicio,
      nombre: this.nombreJornada,
    };
    this.arregloHorarios.push(nuevoHorario);
    console.log(this.arregloHorarios, this.id_dia_laboral);
  }

  //ver bien que pasa con las eliminaciones
  eliminarHorario(id: number, nombre: string) {
    let indice = this.arregloHorarios.findIndex(
      (elementos: any) => nombre === elementos.nombre && id === elementos.id
    );
    //elimina
    this.id_dia_laboral.splice(indice, 1);
    this.arregloHorarios = this.arregloHorarios.filter(
      (elementos: any) => !(nombre === elementos.nombre && id === elementos.id)
    );
    console.log(this.arregloHorarios, this.id_dia_laboral);
  }

  //  para los servicios

  agregarServicio() {
    console.log(this.arregloServiciosEspecificos);

    const usoEstado: estadoServicio = {
      estado: '',
    };

    const nuevaJornada: serviciosPrestados = {
      id: this.arregloServiciosEspecificos.length,
      precio: 0,
      nombre: '',
      estadoServicio: usoEstado,
    };

    const duracionServicio: DuracionServicioPrestado = {
      nombre: '',
      duracion: {
        hours: 0,
        minutes: 0,
      },
      servicioPrestado: nuevaJornada,
    };
    this.arregloServiciosEspecificos.push(nuevaJornada);
    this.arregloDuracionServiciosEspecificos.push(duracionServicio);
    console.log(
      this.arregloServiciosEspecificos,
      this.arregloDuracionServiciosEspecificos
    );
  }

  eliminarServicio(id: number | undefined, nombre: string) {
    this.arregloServiciosEspecificos = this.arregloServiciosEspecificos.filter(
      (elementos: any) => !(nombre === elementos.nombre && id === elementos.id)
    );
    console.log(this.arregloServiciosEspecificos);
  }

  //funcion para crear el servicio
  creacionServicio() {
    const nuevoServicio: servicios = {
      nombre: this.nombreServicio,
      descripcion: this.descripcionServicio,
      imagen: 'wss',
      estadoServicio: this.estadoSerivicioServicio,
      categoria: this.categoriaServicio,
    };
    console.log(this.servicioGenral);

    this.servicioServicio
      .creacionServicio(
        this.usuarioSeleccionado,
        nuevoServicio,
        this.arregloHorarios,
        this.id_dia_laboral,
        this.arregloServiciosEspecificos,
        this.arregloDuracionServiciosEspecificos
      )
      .subscribe({
        next: (response: any) => {
          console.log(response.mensaje, 'simon');
          console.log(response.ok);

          if (response.ok) {
            Swal.fire({
              icon: 'success',
              title: 'Ingreso de Servicio',
              text: 'Ingreso de Servicio exitoso.',
            });
          }
        },
        error: (err) => {
          console.log(err);
          switch (err.status) {
            case HttpStatusCode.NotFound:
              Swal.fire({
                icon: 'warning',
                title: 'Usuario no encontrado',
                text: 'El usuario no existe en el sistema.',
              });
              break;

            case HttpStatusCode.BadRequest:
              Swal.fire({
                icon: 'error',
                title: 'Credenciales Incorrectas',
                text: 'Las credenciales no coinciden, por favor verifique.',
              });
              break;
          }
        },
      });
  }

  ngOnInit(): void {
    this.usuariosServicio
      .obtenerUsuarioRol(12)
      .subscribe((usuariosGenerales: any) => {
        console.log(usuariosGenerales);

        this.todosUsuarios = usuariosGenerales.usuarios;
      });
    this.empresaServicio
      .obtenerInfoEmpresa()
      .subscribe({
        next: (response: any) => {
          this.empresa = response.empresa;
          console.log(this.empresa.tipoServicio);
        },
        error: (err) => {
          console.log(err);
        },
      });

      
    this.categoriaEstadoServicio
      .obtenerTodasCategoriasRegistradas()
      .subscribe((elementos: any) => {
        this.todasCategorias = elementos.categorias;
        console.log(elementos);
      });
  }
}
