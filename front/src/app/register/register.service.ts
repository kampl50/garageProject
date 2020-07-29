import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';


import { environment } from '@environments/environment';
import { IUser } from '@app/services/Iuser';

@Injectable({ providedIn: 'root' })
export class RegisterService {


    constructor(private http: HttpClient) {}


    
    register(data: IUser) {

        const body = data;

        console.log( JSON.stringify(body));
        return this.http.post<any>(`${environment.apiUrl}/register`, JSON.stringify(body),{headers: 
            {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' } } );
    }

}