import { Routes } from '@angular/router';
import { Connect } from '../pages/connect/connect.component';
import { Login } from '../pages/login/login.component';
import { Register } from '../pages/register/register.component';
import { NotFound } from '../pages/notFound/notFound.component';

export const routes: Routes = [
  { path: 'connect', component: Connect },
  { path: 'login', component: Login },
  { path: 'register', component: Register },
  { path: '', redirectTo: '/connect', pathMatch: 'full' },
  { path: '**', component: NotFound },
];
