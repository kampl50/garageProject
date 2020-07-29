import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '@environments/environment';
import { IUser } from './Iuser';
import * as jwt_decode from "jwt-decode";
import { Roles } from '@app/models/roles.enum';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
    private currentUserSubject: BehaviorSubject<any>;
    public currentUser: Observable<any>;

    constructor(private http: HttpClient) {
        this.currentUserSubject = new BehaviorSubject<any>(JSON.parse(localStorage.getItem('currentUser')));
        this.currentUser = this.currentUserSubject.asObservable();
    }

    public get currentUserToken(): string {
        if(this.currentUserSubject.value){
            return this.currentUserSubject.value.token? this.currentUserSubject.value.token:this.currentUserSubject.value
        }
        
    }

    public get userId(): string {
        let user:any = jwt_decode(this.currentUserToken)
        return user.id;
    }

    public get role(): string {
        let user:any = jwt_decode(this.currentUserToken)
        if(user.login == "admin"){
            return Roles.ADMIN
        }
        return user.role;
    }

    login(data: IUser) {
        const body = data;
        return this.http.post(`${environment.apiUrl}/logIn`, JSON.stringify(body),
        {
            headers: 
                {
                    'Content-Type': 'application/json', 
                    'Access-Control-Allow-Origin': '*'
                 },
                 observe: 'response'
        })
            .pipe(
                
                map((user:any) => {
                localStorage.setItem('currentUser', JSON.stringify(user.body));
                this.currentUserSubject.next(user.body.token);
                return user;
            }));
    }

    logout() {
        localStorage.removeItem('currentUser');
        this.currentUserSubject.next(null);
    }
}
