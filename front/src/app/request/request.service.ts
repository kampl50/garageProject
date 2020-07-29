import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';


import { environment } from '@environments/environment';
import { IRequest } from './IRequest';

@Injectable({ providedIn: 'root' })
export class RequestService {


    constructor(private http: HttpClient) {}


    
    request(data: IRequest) {
        const body = data;
        return this.http.post<any>(`${environment.apiUrl}/service/orderVisit`, JSON.stringify(body), {headers: 
            {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' } } );
    }

    editRequest(data: IRequest) {
        const body = data;
        return this.http.post<any>(`${environment.apiUrl}/service/editOrder`, JSON.stringify(body), {headers: 
            {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' } } );
    }

    requestById(id) {
        return this.http.get<any>(`${environment.apiUrl}/service/ordersById?id=${id}`, {headers: 
            {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' } } );
    }

}