import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JornadasLaboralesServicioService } from 'src/app/admin/services/Jornadas/jornadas-laborales-servicio.service';
import { ServiciosService } from 'src/app/admin/services/servicios.service';

@Component({
  selector: 'app-vista-servicio-especifico',
  templateUrl: './vista-servicio-especifico.component.html',
  styleUrls: ['./vista-servicio-especifico.component.css'],
})
export class VistaServicioEspecificoComponent implements OnInit {
  serviciosServicio = inject(ServiciosService);
  jornadasServicio = inject(JornadasLaboralesServicioService);

  elementoPath = inject(ActivatedRoute);

  ngOnInit(): void {
    console.log(this.elementoPath.snapshot.paramMap.get('id'));

    this.serviciosServicio.obtenerTodosServiciosEspecificos(
      Number(this.elementoPath.snapshot.paramMap.get('id'))
    );

    this.jornadasServicio.obtenerTodasJornadasEspecificos(
      Number(this.elementoPath.snapshot.paramMap.get('id'))
    );
  }
}
