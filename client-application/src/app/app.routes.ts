import { Routes } from '@angular/router';
import { RaceListComponent } from './race-list/race-list.component';
import { RaceFormComponent } from './race-form/race-form.component';
import { ApplicationFormComponent } from './application-form/application-form.component';
import { ApplicationListComponent } from './application-list/application-list.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'race-list', component: RaceListComponent, canActivate: [AuthGuard] },
  { path: 'race-form/:id', component: RaceFormComponent, canActivate: [AuthGuard] },
  { path: 'race-form', component: RaceFormComponent, canActivate: [AuthGuard] },
  { path: 'application-form/:id', component: ApplicationFormComponent, canActivate: [AuthGuard] },
  { path: 'application-list', component: ApplicationListComponent, canActivate: [AuthGuard] },
];
