import { Routes } from '@angular/router';
import { Connect } from '../pages/connect/connect.component';
import { Login } from '../pages/login/login.component';
import { Register } from '../pages/register/register.component';
import { NotFound } from '../pages/notFound/notFound.component';
import { Posts } from '../pages/posts/posts.component';
import { AuthGuardService } from '../features/authentication/api/AuthGuardService';
import { ConnectGuardService } from '../pages/connect/ConnectGuardService';

export const routes: Routes = [
  { path: 'connect', component: Connect, canActivate: [ConnectGuardService] },
  { path: 'login', component: Login },
  { path: 'register', component: Register },
  { path: 'posts', component: Posts, canActivate: [AuthGuardService] },
  { path: '', redirectTo: '/connect', pathMatch: 'full' },
  { path: '**', component: NotFound },
];
