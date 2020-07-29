import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';


import { environment } from '@environments/environment';
import { IUser } from '@app/services/Iuser';

@Injectable({ providedIn: 'root' })
export class PartsService {


    constructor(private http: HttpClient) {}


    
    setCost(id, data) {

        const body = data;
        let finalPrice:number = +data.service
        body.parts.forEach(element => {
            finalPrice += +element.price
        });
        return this.http.post(`${environment.apiUrl}/worker/valuateOrder?id=${id}&price=${finalPrice}`, JSON.stringify(body.parts),{headers: 
            {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' } } );
    }

}