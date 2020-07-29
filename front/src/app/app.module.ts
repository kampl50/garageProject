import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

// used to create fake backend
import { fakeBackendProvider } from './helpers';

import { AppComponent } from './app.component';
import { appRoutingModule } from './app.routing';

import { JwtInterceptor, ErrorInterceptor } from './helpers';
import { RequestComponent } from './request/request.component';
import { LandingComponent } from './landing/landing.component';;
import { RegisterComponent } from './register/register.component'
import { LoginComponent } from './login';
import { CommonModule } from '@angular/common';
import { RequestListComponent } from './request/list/request-list.component';
import { RequestDetailsComponent } from './request/details/request-details.component';
import { UserListComponent } from './Users/List/user-list.component';
import { UserDetailComponent } from './Users/details/user-details.component';
import { PreferencesComponent } from './preferences/preferences.component';
import { PartsComponent } from './request/parts/parts.component';
import { ContactComponent } from './contact/contact.component';
@NgModule({
    imports: [
        BrowserModule,
        ReactiveFormsModule,
        HttpClientModule,
        appRoutingModule,
        CommonModule
    ],
    declarations: [
        AppComponent,
        RequestComponent,
        LoginComponent,
        LandingComponent,
        RegisterComponent,
        RequestListComponent,
        RequestDetailsComponent,
        UserListComponent,
        UserDetailComponent,
        PreferencesComponent,
        PartsComponent,
        ContactComponent,
    ],
    providers: [
        { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
        { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }