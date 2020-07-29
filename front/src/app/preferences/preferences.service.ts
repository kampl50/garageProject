import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';


import { environment } from '@environments/environment';
import { IUser } from '@app/services/Iuser';

@Injectable({ providedIn: 'root' })
export class PreferencesService {


    constructor(private http: HttpClient) {}


    
    setPreferences(id, data) {

        const body = data;

        return this.http.post(`${environment.apiUrl}/worker/setPreferences?id=${id}&experienceNumber=${data.exp}&preferMarkFirst=${data.mark}&preferMarkSecond=${data.mark2}&preferMarkThird=${data.mark3}`,{headers: 
            {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' } } );
    }

}