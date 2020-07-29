import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';


import { environment } from '@environments/environment';
import { filter, tap, map } from 'rxjs/operators';
import { IRequest } from '../IRequest';

@Injectable({ providedIn: 'root' })
export class RequestListService {


    constructor(private http: HttpClient) {}


    
    requestUserList(id) {
        return this.http.get<any>(`${environment.apiUrl}/service/ordersByClientId?id=${id}`, {headers: 
            {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' } } );
    }

    requestAdminList() {
        return this.http.get<any>(`${environment.apiUrl}/service/orders`, {headers: 
            {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' } } );
    }

    requestWorkerList(id) {
        return this.http.get<any>(`${environment.apiUrl}/service/ordersByWorkerId?id=${id}`, {headers: 
            {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' } }  );
    }

    attachRequest(workerId, requestId){
        return this.http.post<any>(`${environment.apiUrl}/service/addWorker?workerId=${workerId}&notificationId=${requestId}`, {headers: 
            {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' } } );
    }

    changeStatus(request:IRequest){
        const body = request;
        return this.http.post<any>(`${environment.apiUrl}/service/editOrder`, JSON.stringify(body), {headers: 
            {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' } } );
    }

    accept(id, yesOrNo){

        return this.http.post<any>(`${environment.apiUrl}/service/acceptValuatedOrder?id=${id}&isAccepted=${yesOrNo}`, {headers: 
            {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' } } );
    }

}