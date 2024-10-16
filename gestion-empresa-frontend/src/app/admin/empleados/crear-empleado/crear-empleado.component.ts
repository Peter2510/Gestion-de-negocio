import { Component, OnInit, inject } from '@angular/core';
import { ServicioAuthService } from 'src/app/auth/services/servicio-auth.service';
import { Persona } from 'src/app/models/Persona';
import { Usuario } from 'src/app/models/Usuario';
import { RolesService } from '../../services/roles/roles.service';
import { Rol } from 'src/app/models/Roles';

@Component({
  selector: 'app-crear-empleado',
  templateUrl: './crear-empleado.component.html',
  styleUrls: ['./crear-empleado.component.css']
})
export class CrearEmpleadoComponent implements OnInit{

  servicioAuth = inject(ServicioAuthService);
  public roles:Rol[] = [];


  constructor(private rolService:RolesService){
  }

  ngOnInit(): void {
    this.obtenerRolesRegistrados();
  }

  obtenerRolesRegistrados(){
    this.rolService.obtenerRolesRegistrados().subscribe({
      next:(data)=>{

        this.roles = data.roles

      },error:(error)=>{
        console.log(error)
      }
    })
  }

  
 
}
