import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { categorias_servicios } from 'src/app/models/Servicios';
import { CategoriasServicioService } from '../../services/categorias/categorias-servicio.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-crear-categoria',
  templateUrl: './crear-categoria.component.html',
  styleUrls: ['./crear-categoria.component.css']
})
export class CrearCategoriaComponent {

  loading: boolean = false;

  categoria: categorias_servicios = {
    id: 0,
    tipo: ""
  };

  constructor(private categoriaService: CategoriasServicioService, private router: Router) { }

  crearCategoria() {

    if (this.categoria.tipo.length < 1) {
      Swal.fire({
        title: 'Debes ingresar un nombre a la categoria',
        icon: 'info'
      });
      return
    }
    this.loading = true;
    this.categoriaService.crearCategoria(this.categoria).subscribe({
      next: (data) => {
        Swal.fire({
          title: data.mensaje,
          icon: 'success'
        }).then(() => {
          this.loading = false;
          this.router.navigate(['administrador/categorias-registradas']);
        });
      }, error: (error) => {
        console.log(error);
        Swal.fire({
          title: error.error.mensaje,
          icon: 'error'
        });
        this.loading = false;
      }
    })

  }

}
