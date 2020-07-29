import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '@app/services';
import { first } from 'rxjs/operators';
import { UserDetailsService } from './user-details.service';
import { IUser } from '@app/services/Iuser';
import { IUserDetail } from '@app/models/userDetails';

@Component({
  selector: 'app-register',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss']
})
export class UserDetailComponent implements OnInit {
  loginForm: FormGroup;
    loading = false;
    submitted = false;
    returnUrl: string;
    error = '';
    id: string;

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private userDetailService: UserDetailsService
    ) { 
    }

    ngOnInit() {
        this.route.params.subscribe(params => {
            this.id = params['id'];
         });
        this.loginForm = this.formBuilder.group({
            login: ['', Validators.required],
            password: ['', Validators.required],
            name: ['', Validators.required],
            surname: ['', Validators.required],
            numberPhone: ['', Validators.required],
            role: ['', Validators.required],
            addressEmail: ['', Validators.required],
        });
        if(this.id){
            this.patchValues();
        }
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    }


    get f() { return this.loginForm.controls; }

    private patchValues(){
       this.userDetailService.getUser(this.id).subscribe(
            res => this.loginForm.patchValue(res)
        );
        
    }

    onSubmit() {
        this.submitted = true;

        if (this.loginForm.invalid) {
            return;
        }
        const userData: IUserDetail = {
            login:  this.f.login.value,
            password: this.f.password.value,
            name: this.f.name.value,
            surname: this.f.surname.value,
            numberPhone: this.f.numberPhone.value,
            role: this.f.role.value,
            addressEmail: this.f.addressEmail.value,
        }

        this.loading = true;
        if(this.id){
        userData.id = this.id
        this.userDetailService.editUser(userData)
        .pipe(first())
        .subscribe(
            data => {
                this.router.navigate([this.returnUrl]);
            },
            error => {
                this.error = error;
                this.loading = false;
            },
            () => {
                alert('Pomyślnie edytowano użyytkownika');
                this.router.navigate(['/user-list']);
            }
            )
        }else{
            this.userDetailService.addUser(userData)
            .pipe(first())
            .subscribe(
                data => {
                    this.router.navigate([this.returnUrl]);
                },
                error => {
                    this.error = error;
                    this.loading = false;
                },
                () => {
                    alert('Pomyślnie dodano użyytkownika');
                    this.router.navigate(['/user-list']);
                }
                );
        }

    }

}
