import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '@app/services';
import { first } from 'rxjs/operators';
import { PartsService } from './parts.service';
import { IUser } from '@app/services/Iuser';

@Component({
  selector: 'app-register',
  templateUrl: './parts.component.html',
})
export class PartsComponent implements OnInit {
  partsForm: FormGroup;
    loading = false;
    submitted = false;
    returnUrl: string;
    error = '';
    id: string;

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private partsService: PartsService,
        private authService: AuthenticationService
    ) { 
    }

    ngOnInit() {
        this.route.params.subscribe(params => {
            this.id = params['id'];
         });
        this.partsForm = this.formBuilder.group({
            name: ['', Validators.required],
            price: ['', Validators.required],
            name2: [''],
            price2: [''],
            name3: [''],
            price3: [''],
            service: ['', Validators.required],
        });

        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    }


    get f() { return this.partsForm.controls; }

    onSubmit() {
        this.submitted = true;

        if (this.partsForm.invalid) {
            return;
        }
        const data = {
            parts: [
                {
                    name:  this.f.name.value,
                    price: this.f.price.value,
                },
                {
                    name:  this.f.name2.value,
                    price: this.f.price2.value,
                },
                {
                    name:  this.f.name3.value,
                    price: this.f.price3.value,
                }
            ],
            service: this.f.service.value
        }

        this.loading = true;
        this.partsService.setCost(this.id, data)
            .pipe(first())
            .subscribe(
                () => {
                    alert('Dane zostały zaktualizowane pomyślnie');
                    this.router.navigate(['/request-list', this.authService.userId]);
                },
                error => {
                    this.error = error;
                    this.loading = false;
                });
    }

}
