import { Component } from '@angular/core';
import { first, tap } from 'rxjs/operators';

import { User } from '@app/models';
import { UserService, AuthenticationService } from '@app/services';
import { Validators, FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { IUser } from '@app/services/Iuser';
import { IRequest } from './IRequest';
import { RequestService } from './request.service';
import { Roles } from '@app/models/roles.enum';

@Component({ templateUrl: 'request.component.html' })
export class RequestComponent {
    requestForm: FormGroup;
    loading = false;
    submitted = false;
    returnUrl: string;
    error = '';
    id: string;

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private authenticationService: AuthenticationService,
        private requestService: RequestService
    ) { 
        if (!this.authenticationService.currentUserToken) { 
            this.router.navigate(['/']);
        }
    }


    ngOnInit() {
        this.route.params.subscribe(params => {
            this.id = params['id'];
         });
        this.requestForm = this.formBuilder.group({
            mark: ['', Validators.required],
            model: ['', Validators.required],
            dateProduction: ['', Validators.required],
            kindEngine: ['', Validators.required],
            power: ['', Validators.required],
            dateVisit: ['', Validators.required],
            problemDescription: ['', Validators.required],
            kindNotification: ['', Validators.required],
        });
        this.addControls();
        this.patchValues();
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    }

    get f() { return this.requestForm.controls; }

    private patchValues(){

         if(this.id && this.authenticationService.role == Roles.ADMIN){
            this.requestService.requestById(this.id).subscribe(
                res => this.setFormValues(res),
                err => alert(err)
            )
         }
         
    }
    private setFormValues(data: IRequest){
        
        this.requestForm.patchValue({
            mark: data.clientCar.mark,
            model: data.clientCar.model,
            dateProduction: data.clientCar.dateProduction,
            kindEngine: data.clientCar.kindEngine,
            power: data.clientCar.power,
            dateVisit: data.dateVisit,
            problemDescription: data.problemDescription,
            kindNotification: data.kindNotification,
            statusNotification: data.statusNotification,
            workerId: data.nameAndSurnameWorkers?data.nameAndSurnameWorkers[0]:null,
            clientId: data.clientId,
            price: data.price
        })
        
    }

    private addControls(){
        if(this.id){
            this.requestForm.addControl('statusNotification', new FormControl('', Validators.required));
            this.requestForm.addControl('workerId', new FormControl({value:'', disabled: true}, Validators.required));
            this.requestForm.addControl('clientId', new FormControl({value:'', disabled: true}, Validators.required));
            this.requestForm.addControl('price', new FormControl({value:'', disabled: false}, Validators.required));
        }
      
    }
    onSubmit() {
        let userId = this.authenticationService.userId;

        this.submitted = true;
        if (this.requestForm.invalid) {
            return;
        }

        this.loading = true;

        const requestData: IRequest = {
            clientId: this.id? this.f.clientId.value: userId,
            workerId: this.f.workerId? this.f.workerId.value: null,
            price: this.f.price? this.f.price.value: null,
            dateVisit: this.f.dateVisit.value,
            problemDescription:this.f.problemDescription.value,
            kindNotification:this.f.kindNotification.value,
            statusNotification: this.f.statusNotification? this.f.statusNotification.value : "NEW",
            clientCar: {
                mark:this.f.mark.value,
                model:this.f.model.value,
                dateProduction: this.f.dateProduction.value,
                kindEngine: this.f.kindEngine.value,
                power:this.f.power.value,
            }
        }
        if(this.id){
            requestData.id = this.id;
            this.requestService.editRequest(requestData).subscribe(
                undefined,
                error => {
                    this.error = error;
                    this.loading = false;
                },
                () => alert("edytowano zlecenie pomyślnie")
            );
        }else{
            this.requestService.request(requestData).subscribe(
                undefined,
                error => {
                    this.error = error;
                    this.loading = false;
                },
                () => alert("dodano zlecenie pomyślnie")
            );
        }
        this.router.navigate(['/request-list']);

    }
}