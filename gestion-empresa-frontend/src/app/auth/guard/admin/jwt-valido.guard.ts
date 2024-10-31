import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { ServicioAuthService } from '../../services/servicio-auth.service';


export const jwtValidoGuard: CanActivateFn = (route, state) => {
  
  const authService = inject(ServicioAuthService); 
  const router = inject(Router);


  if(authService.getIdTipoUsuario()== null){
    router.navigate(['/auth/login']);
    return false;
  }

  return true;
};






