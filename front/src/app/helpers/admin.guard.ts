import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

import { AuthenticationService } from '@app/services';
import { Roles } from '@app/models/roles.enum';

@Injectable({ providedIn: 'root' })
export class AdminGuard implements CanActivate {
    constructor(
        private router: Router,
        private authenticationService: AuthenticationService
    ) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        
        const role = this.authenticationService.role;
        if (role == Roles.ADMIN) {
            return true;
        }
        this.router.navigate(['/'], { queryParams: { returnUrl: state.url } });
        return false;
    }
}