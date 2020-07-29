import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '@app/services';
import { first } from 'rxjs/operators';
import { RegisterService } from './register.service';
import { IUser } from '@app/services/Iuser';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  loginForm: FormGroup;
    loading = false;
    submitted = false;
    returnUrl: string;
    error = '';

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private registerService: RegisterService
    ) { 
    }

    ngOnInit() {
        this.loginForm = this.formBuilder.group({
            login: ['', Validators.required],
            password: ['', Validators.required],
            name: ['', Validators.required],
            surname: ['', Validators.required],
            numberPhone: ['', Validators.required],
            addressEmail: ['', Validators.required],
        });

        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    }


    get f() { return this.loginForm.controls; }

    onSubmit() {
        this.submitted = true;

        if (this.loginForm.invalid) {
            return;
        }
        const registerData: IUser = {
            login:  this.f.login.value,
            password: this.f.password.value,
            name: this.f.name.value,
            surname: this.f.surname.value,
            numberPhone: this.f.numberPhone.value,
            role: 'USER',
            addressEmail: this.f.addressEmail.value,
        }

        this.loading = true;
        this.registerService.register(registerData)
            .pipe(first())
            .subscribe(
                () => {
                    this.router.navigate(['/login']);
                },
                error => {
                    this.error = error;
                    this.loading = false;
                });
    }

}
