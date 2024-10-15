import { Component, OnInit } from '@angular/core';
import { Rol } from 'src/app/models/Roles';
import { RolesService } from '../../services/roles/roles.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ver-roles',
  templateUrl: './ver-roles.component.html',
  styleUrls: ['./ver-roles.component.css']
})
export class VerRolesComponent implements OnInit{

  public roles:Rol[] = [];

  constructor(private rolService:RolesService, private router: Router){

  }
  
  ngOnInit(): void {
    this.rolService.obtenerRolesRegistrados().subscribe({
      next:(data)=>{

        console.log(data)
        this.roles = data.roles

      },error:(error)=>{
        console.log(error)
      }
    })
  }

  detallesRol(id: number) {
    this.rolService.setRolId(id);
    console.log("pedi rol", id)
    
    this.router.navigate(['administrador/detalles-rol']);
    
  }



}
