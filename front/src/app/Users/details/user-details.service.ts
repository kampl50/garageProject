import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';


import { environment } from '@environments/environment';
import { IUser } from '@app/services/Iuser';
import { IUserDetail } from '@app/models/userDetails';

@Injectable({ providedIn: 'root' })
export class UserDetailsService {


    constructor(private http: HttpClient) {}


    
    addUser(data: IUserDetail) {

        const body = data;
        return this.http.post<any>(`${environment.apiUrl}/register`, JSON.stringify(body),{headers: 
            {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' } } );
    }

    getUser(id) {
        return this.http.post<any>(`${environment.apiUrl}/admin/personById?id=${id}`, {headers: 
            {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' } } );
    }

    editUser(body) {
        return this.http.post<any>(`${environment.apiUrl}/admin/editPerson}`, JSON.stringify(body),{headers: 
            {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' } } );
    }

}