import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '@app/services';
import { first } from 'rxjs/operators';
import { PreferencesService } from './preferences.service';
import { IUser } from '@app/services/Iuser';

@Component({
  selector: 'app-register',
  templateUrl: './preferences.component.html',
})
export class PreferencesComponent implements OnInit {
  preferencesForm: FormGroup;
    loading = false;
    submitted = false;
    returnUrl: string;
    error = '';

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private preferencesService: PreferencesService,
        private authService: AuthenticationService
    ) { 
    }

    ngOnInit() {
        this.preferencesForm = this.formBuilder.group({
            mark: ['', Validators.required],
            mark2: ['', Validators.required],
            mark3: ['', Validators.required],
            exp: ['', Validators.required],
        });

        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    }


    get f() { return this.preferencesForm.controls; }

    onSubmit() {
        this.submitted = true;

        if (this.preferencesForm.invalid) {
            return;
        }
        const data = {
            mark:  this.f.mark.value,
            mark2: this.f.mark2.value,
            mark3: this.f.mark3.value,
            exp: this.f.exp.value,
        }

        this.loading = true;
        this.preferencesService.setPreferences(this.authService.userId, data)
            .pipe(first())
            .subscribe(
                () => {
                    alert('Dane zostały zaktualizowane pomyślnie');
                    this.router.navigate(['/']);
                },
                error => {
                    this.error = error;
                    this.loading = false;
                });
    }

}
