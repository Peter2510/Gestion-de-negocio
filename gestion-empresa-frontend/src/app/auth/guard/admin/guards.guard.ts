import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { ServicioAuthService } from '../../services/servicio-auth.service';

export const permissionGuard: CanActivateFn = async (route, state) => {
  const authService = inject(ServicioAuthService); 
  const router = inject(Router);
  const requiredPermissions = route.data['permissions'];

  const hasPermission = await Promise.all(
    requiredPermissions.map(permission => authService.tienePermiso(permission))
  );

  if (hasPermission.some(has => has)) {
    return true;
  } else {
    router.navigate(['/administrador/acceso-denegado']);
    return false;
  }
};
