import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';


import { environment } from '@environments/environment';

@Injectable({ providedIn: 'root' })
export class RequestDetailsService {


    constructor(private http: HttpClient) {}


    
    requestById(id) {
        return this.http.get<any>(`${environment.apiUrl}/service/ordersById?id=${id}`, {headers: 
            {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' } } );
    }

    pay(body){
        return this.http.post<any>(`${environment.apiUrl}/pay`, JSON.stringify(body), {headers: 
            {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' } } );
    }
    

}