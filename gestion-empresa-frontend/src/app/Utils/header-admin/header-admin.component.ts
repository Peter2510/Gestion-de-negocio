import { Component, OnInit } from '@angular/core';
import { Empresa } from 'src/app/admin/models/Empresa';
import { EmpresaService } from 'src/app/admin/services/empresa/empresa.service';

@Component({
  selector: 'app-header-admin',
  templateUrl: './header-admin.component.html',
  styleUrls: ['./header-admin.component.css']
})
export class HeaderAdminComponent implements OnInit {

  empresa: Empresa | null = null; //inicializar como null para manejar el estado de datos no cargados
  loading: boolean = true; //bandera para controlar si los datos están cargando

  constructor(private empresaService: EmpresaService) { }

  ngOnInit(): void {
    this.empresaService.obtenerInfoEmpresa().subscribe({
      next: (data) => {
        this.empresa = data.empresa; 
        this.loading = false; //desactivar la bandera de carga cuando los datos estén listos
      },
      error: (error) => {
        console.log(error);
        this.loading = false;
      }
    });
  }
}