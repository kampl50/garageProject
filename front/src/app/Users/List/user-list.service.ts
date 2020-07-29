import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';


import { environment } from '@environments/environment';

@Injectable({ providedIn: 'root' })
export class UserListService {


    constructor(private http: HttpClient) {}


    
    requestUserList(id) {
        return this.http.get<any>(`${environment.apiUrl}/service/ordersByClientId?id=${id}`, {headers: 
            {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' } } );
    }

    userAdminList() {
        return this.http.get<any>(`${environment.apiUrl}/admin/allPeople`, {headers: 
            {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' } } );
    }

    deleteUser(id) {
        return this.http.post<any>(`${environment.apiUrl}/admin/deletePerson?id=${id}`, {headers: 
            {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' } } );
    }

}