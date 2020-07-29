import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AuthenticationService } from './services';
import { User } from './models';
import * as jwt_decode from 'jwt-decode';

@Component({ 
    selector: 'app', 
    templateUrl: 'app.component.html',
    styleUrls:['app.component.scss'] 
})
export class AppComponent {
    currentUser: string;
    role: string;
    id:string;

    constructor(
        private router: Router,
        private authenticationService: AuthenticationService
    ) {
        this.authenticationService.currentUser.subscribe(data => {
            let user:any;
            this.currentUser = data;
            if(data){
                data.token? user = jwt_decode(data.token): user = jwt_decode(data) 
                user.login == "admin"?this.role = "ADMIN":this.role = user.role
                this.id = user.id
            }

            

        });
    }

    logout() {
        this.authenticationService.logout();
        this.router.navigate(['/']);
    }
}