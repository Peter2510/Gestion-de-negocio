import { Component, inject, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CategoriasServicioService } from '../../services/categorias/categorias-servicio.service';
import { dias_laborales, jornada_laboral } from 'src/app/models/Jornadas';
import { Time } from '@angular/common';
import {
  DuracionServicioPrestado,
  estadoServicio,
  serviciosPrestados,
} from 'src/app/models/Servicios';

@Component({
  selector: 'app-creacion-servicio',
  templateUrl: './creacion-servicio.component.html',
  styleUrls: ['./creacion-servicio.component.css'],
})
export class CreacionServicioComponent implements OnInit {
  servicioForm: FormGroup;
  arregloHorarios: any = [];
  diasParaHorario: dias_laborales[] = [];
  arregloServiciosEspecificos: serviciosPrestados[] = [];
  todasCategorias: any;
  currentStep = 1;
  // valores para las jornadas
  horaInicio!: Time;
  horaFin!: Time;
  nombreJornada!: string;
  estados = [
    { id: 1, nombre: 'Activo' },
    { id: 2, nombre: 'Inactivo' },
  ];
  categorias = [
    { id: 1, nombre: 'Tecnología' },
    { id: 2, nombre: 'Consultoría' },
    // Agrega más categorías si es necesario
  ];

  //servicio
  categoriaEstadoServicio = inject(CategoriasServicioService);

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

  // para agregar mas
  agregarHorario() {
    const nuevoHorario: jornada_laboral = {
      id: this.arregloHorarios.length,
      hora_fin: this.horaFin,
      hora_inicio: this.horaInicio,
      nombre: this.nombreJornada,
    };
    this.arregloHorarios.push(nuevoHorario);
    console.log(this.arregloHorarios);
  }

  eliminarHorario(id: number, nombre: string) {
    this.arregloHorarios = this.arregloHorarios.filter(
      (elementos: any) => !(nombre === elementos.nombre && id === elementos.id)
    );
    console.log(this.arregloHorarios);
  }

  //  para los servicios

  agregarServicio() {
    const usoEstado: estadoServicio = {
      estado: '',
    };
    const duracionServicio: DuracionServicioPrestado = {
      nombre: '',
      duracion: {
        hours: 0,
        minutes: 0,
      },
    };
    const nuevaJornada: serviciosPrestados = {
      id: this.arregloServiciosEspecificos.length,
      estado: '',
      nombre: '',
      estadoSerivicio: usoEstado,
      duracionServicioPrestado: duracionServicio,
    };
    this.arregloServiciosEspecificos.push(nuevaJornada);
    console.log(this.arregloServiciosEspecificos);
  }

  eliminarServicio(id: number | undefined, nombre: string) {
    this.arregloServiciosEspecificos = this.arregloServiciosEspecificos.filter(
      (elementos: any) => !(nombre === elementos.nombre && id === elementos.id)
    );
    console.log(this.arregloServiciosEspecificos);
  }
  ngOnInit(): void {
    this.categoriaEstadoServicio
      .obtenerTodasCategoriasRegistradas()
      .subscribe((elementos: any) => {
        this.todasCategorias = elementos;
        console.log(elementos);
      });
  }
}
