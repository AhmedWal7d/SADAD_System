import { Routes } from '@angular/router';
import { LoginComponent } from './Components/login/login.component';
import { DashboardComponent } from './Components/dashboard/dashboard.component';

export const routes: Routes = [
    {path:"" , component:LoginComponent},
    {path:"dashboard" , component:DashboardComponent},
];
