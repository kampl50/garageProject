import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {  AuthenticationService } from '@app/services';
import {  Router, ActivatedRoute } from '@angular/router';
import { RequestListService } from './request-list.service';
import { IRequest } from '../IRequest';
import { Observable } from 'rxjs/internal/Observable';
import { Roles } from '@app/models/roles.enum';

@Component({ templateUrl: 'request-list.component.html' })
export class RequestListComponent {
    loading = false;
    submitted = false;
    returnUrl: string;
    error = '';
    id: string;
    role: string;
    requestItems: Observable<IRequest[]>;
    buttonText: string;

    constructor(
        private router: Router,
        private authenticationService: AuthenticationService,
        private requestListService:  RequestListService,
        private route: ActivatedRoute
    ) { 
        if (!this.authenticationService.currentUserToken) { 
            this.router.navigate(['/']);
        }
    }


    ngOnInit() {
        this.route.params.subscribe(params => {
            this.id = params['id'];
         });
        this.submitted = true;
        this.loading = true;
        this.role = this.authenticationService.role;
        console.log(this.role);
        this.renderList();
    }

    private renderList(){
        let role = this.authenticationService.role
        switch(role){
            case Roles.USER:
                this.requestItems = this.requestListService.requestUserList(this.authenticationService.userId);
                break;
            case Roles.ADMIN:
                this.requestItems = this.requestListService.requestAdminList();
                break;
            case Roles.WORKER:
                this.id?
                    this.requestItems = this.requestListService.requestWorkerList(this.id)
                    :
                    this.requestItems = this.requestListService.requestAdminList();
                break;

        }
    }

    attachToMe(id){
        this.requestListService.attachRequest(this.authenticationService.userId,id).subscribe(
            undefined,
            undefined,
            () => alert('Pomyślnie przydzielono zlecenie do Ciebie')
        )
    }

    accept(id, accepted:boolean){
        let yesOrNo:string;
        accepted? yesOrNo ='YES' : yesOrNo = 'NO'

        this.requestListService.accept(id,yesOrNo).subscribe(
            () => {
                accepted? alert('Zakceptowano wycenę') : alert('Odrzucono wycenę');
                this.requestItems = this.requestListService.requestUserList(this.authenticationService.userId);
            }
        );
    }

    changeStatus(item:IRequest){
        switch (item.statusNotification){
            case "IN_PROGRESS":
                item.statusNotification = "PAYMENT"
            break;
            case "PAYMENT":
                item.statusNotification = "IN_PROGRESS"
                break;
        }
        this.requestListService.changeStatus(item).subscribe(
            undefined,
            error => {
                this.error = error;
                this.loading = false;
            },
            () => {
                alert("Zmieniono status pomyślnie");
                this.renderList();
            }
        );
    }


}