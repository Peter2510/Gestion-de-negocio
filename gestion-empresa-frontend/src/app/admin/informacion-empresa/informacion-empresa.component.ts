import { Component, OnInit } from '@angular/core';
import { EmpresaService } from '../services/empresa/empresa.service';
import { Empresa, TipoAsignacionCita, TipoServicio } from '../../models/Empresa';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-informacion-empresa',
  templateUrl: './informacion-empresa.component.html',
  styleUrls: ['./informacion-empresa.component.css']
})
export class InformacionEmpresaComponent implements OnInit {

  constructor(private empresaService: EmpresaService) { }

  selectedFile: File | null = null;
  tiposServicios: TipoServicio[] = [];
  tipoAsignacionCitas: TipoAsignacionCita[] = [];

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
    tipoServicio: { id: 0, nombre: '' },
    tipoAsignacionCita: { id: 0, tipo: '', activo: true }
  };



  loading: boolean = true; //bandera para controlar la carga


  ngOnInit(): void {
    this.obtenerTipoServicios();
    this.cargarInfoEmpresa();
    this.obtenerTipoAsignacionCita();
  }

  cargarInfoEmpresa(): void {
    this.empresaService.obtenerInfoEmpresa().subscribe({
      next: (data) => {
        this.empresa = data.empresa;
        this.loading = false;
        console.log(this.empresa);
      },
      error: (error) => {
        console.log(error);
        this.loading = false;
      }
    });
  }

  obtenerTipoServicios(): void {
    this.empresaService.obtenerTipoServicios().subscribe({
      next: (data) => {
        this.tiposServicios = data.tiposServicio;
        console.log(data);
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

  obtenerTipoAsignacionCita(): void {
    this.empresaService.obtenerTipoAsignacionCita().subscribe({
      next: (data) => {
        this.tipoAsignacionCitas = data.tiposAsignacionCita;
        console.log(data, "tipoAsignacionCitas");
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

  onTipoAsignacionChange(event: Event): void {
    const selectedId = (event.target as HTMLSelectElement).value;
    const selectedTipoAsignacion = this.tipoAsignacionCitas.find(tac => tac.id.toString() === selectedId);

    if (selectedTipoAsignacion) {
      this.empresa.tipoAsignacionCita.tipo = selectedTipoAsignacion.tipo;
    }
  }


  onTipoServicioChange(event: Event): void {
    const selectedId = (event.target as HTMLSelectElement).value;
    const selectedTipoServicio = this.tiposServicios.find(ts => ts.id.toString() === selectedId);

    if (selectedTipoServicio) {
      this.empresa.tipoServicio.nombre = selectedTipoServicio.nombre;
    }
  }


  actualizarEmpresa(): void {
    this.loading = true;
    console.log(this.empresa);
    this.empresaService.actualizarEmpresa(this.empresa, this.selectedFile).subscribe({
      next: (data) => {
        console.log(data);
        this.loading = false;
        Swal.fire({
          title: 'Empresa actualizada',
          text: 'La empresa se ha actualizado correctamente',
          icon: 'success',
          confirmButtonText: 'Aceptar'
        });
      },
      error: (error) => {
        this.loading = false;
        Swal.fire({
          title: 'Error al actualizar',
          text: 'Ha ocurrido un error al actualizar la empresa',
          icon: 'error',
          confirmButtonText: 'Aceptar'
        });
        console.log(error);
      }
    });
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.empresa.logo = e.target.result;
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }

}
