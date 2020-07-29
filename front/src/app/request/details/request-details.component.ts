import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {  AuthenticationService } from '@app/services';
import {  Router, ActivatedRoute } from '@angular/router';
import { RequestDetailsService } from './request-details.service';
import { IRequest } from '../IRequest';
import { Observable } from 'rxjs/internal/Observable';

@Component({ templateUrl: 'request-details.component.html' })
export class RequestDetailsComponent {
    loading = false;
    submitted = false;
    returnUrl: string;
    error = '';
    id: string;
    request: IRequest;
    role:string;
    linkToPay: string;

    constructor(
        private router: Router,
        private authenticationService: AuthenticationService,
        private requestDetailsService:  RequestDetailsService,
        private route: ActivatedRoute
    ) { 
        if (!this.authenticationService.currentUserToken) { 
            this.router.navigate(['/']);
        }
    }


    ngOnInit() {
        this.role = this.authenticationService.role;
        this.route.params.subscribe(params => {
            this.id = params['id'];
         });
        this.submitted = true;

        this.loading = true;

        this.requestDetailsService.requestById(this.id).subscribe(
        res => this.request = res
        );
        
    }


    pay(){
        let data = {
            notificationID: this.request.id,
            clientID: this.authenticationService.userId,
            description: this.request.problemDescription,
            price: this.request.price,
            payed: true
        }
        this.requestDetailsService.pay(data).subscribe(
            (link) => {
                this.linkToPay = link.redirectUri},
            undefined,
            () => alert("Kliknij w link aby przekierować na stronę płatnosci")
        )
    }



}