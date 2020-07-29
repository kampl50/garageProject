import { Routes, RouterModule } from '@angular/router';

import { RequestComponent } from './request/request.component';
import { AuthGuard } from './helpers';
import { LandingComponent } from './landing/landing.component';
import { LoginComponent } from './login';
import { RegisterComponent } from './register/register.component';
import { RequestListComponent } from './request/list/request-list.component';
import { RequestDetailsComponent } from './request/details/request-details.component';
import { UserListComponent } from './Users/List/user-list.component';
import { AdminGuard } from './helpers/admin.guard';
import { UserDetailComponent } from './Users/details/user-details.component';
import { PreferencesComponent } from './preferences/preferences.component';
import { PartsComponent } from './request/parts/parts.component';
import { ContactComponent } from './contact/contact.component';

const routes: Routes = [
    { path: '', component: LandingComponent },
    { path: 'request/:id', component: RequestComponent, canActivate: [AuthGuard, AdminGuard] },
    { path: 'request', component: RequestComponent, canActivate: [AuthGuard] },
    { path: 'request-list/:id', component: RequestListComponent, canActivate: [AuthGuard] },
    { path: 'request-list', component: RequestListComponent, canActivate: [AuthGuard] },
    { path: 'request-list/details/:id', component:  RequestDetailsComponent, canActivate: [AuthGuard] },
    { path: 'valuate/:id', component: PartsComponent, canActivate: [AuthGuard] },
    { path: 'user-list', component: UserListComponent, canActivate: [AuthGuard] },
    { path: 'user-details/:id', component: UserDetailComponent, canActivate: [AuthGuard, AdminGuard] },
    { path: 'user-details', component: UserDetailComponent, canActivate: [AuthGuard, AdminGuard] },
    { path: 'preferences', component: PreferencesComponent, canActivate: [AuthGuard] },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'contact', component: ContactComponent },
    { path: '**', redirectTo: '' }
];

export const appRoutingModule = RouterModule.forRoot(routes);