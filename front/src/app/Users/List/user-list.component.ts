import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {  AuthenticationService } from '@app/services';
import {  Router } from '@angular/router';
import { UserListService } from './user-list.service';
import { Observable } from 'rxjs/internal/Observable';
import { Roles } from '@app/models/roles.enum';
import { IUserDetail } from '@app/models/userDetails';

@Component({ templateUrl: 'user-list.component.html' })
export class UserListComponent {
    loading = false;
    submitted = false;
    returnUrl: string;
    error = '';
    requestItems: Observable<IUserDetail[]>;

    constructor(
        private router: Router,
        private authenticationService: AuthenticationService,
        private userListService:  UserListService
    ) { 
        if (!this.authenticationService.currentUserToken) { 
            this.router.navigate(['/']);
        }
    }


    ngOnInit() {
        this.submitted = true;
        this.loading = true;
        this.renderList();
    }

    private renderList(){
            this.requestItems = this.userListService.userAdminList();
        }

    deleteUser(id){
        console.log(id);
        this.userListService.deleteUser(id).subscribe(
            undefined,
            undefined,
            () => {
                alert('pomyslnie usunąłeś użytkownika');
                this.renderList();
            }
        )
    }
}


