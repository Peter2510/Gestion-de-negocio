import { Component, OnInit } from '@angular/core';
import { Empresa } from 'src/app/models/Empresa';
import { EmpresaService } from 'src/app/admin/services/empresa/empresa.service';

@Component({
  selector: 'app-header-usuario',
  templateUrl: './header-usuario.component.html',
  styleUrls: ['./header-usuario.component.css'],
})
export class HeaderUsuarioComponent implements OnInit {
  empresa: Empresa | null = null; //inicializar como null para manejar el estado de datos no cargados
  loading: boolean = true; //bandera para controlar si los datos están cargando

  constructor(private empresaService: EmpresaService) {}

  ngOnInit(): void {
    this.empresaService.obtenerInfoEmpresa().subscribe({
      next: (data) => {
        this.empresa = data.empresa;
        this.loading = false; //desactivar la bandera de carga cuando los datos estén listos
        console.log(this.empresa, data.empresa, this.loading);
      },
      error: (error) => {
        console.log(error);
        this.loading = false;
      },
    });
  }
}
