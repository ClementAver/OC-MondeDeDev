import { Routes } from '@angular/router';
import { Connect } from '../pages/connect/connect.component';
import { Login } from '../pages/login/login.component';
import { Register } from '../pages/register/register.component';
import { NotFound } from '../pages/notFound/notFound.component';
import { Posts } from '../pages/posts/posts.component';
import { PostDetails } from '../pages/post-details/post-details.component';
import { AuthGuard } from '../features/authentication/guards/AuthGuard';
import { ConnectGuard } from '../pages/connect/ConnectGuard';
import { Profile } from '../pages/profile/profile.component';
import { Topics } from '../pages/topics/topics.component';
import { PostCreate } from '../pages/post-create/post-create.component';

export const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'connect',
        component: Connect,
        canActivate: [ConnectGuard],
      },
      { path: 'login', component: Login },
      { path: 'register', component: Register },
      { path: 'profile', component: Profile, canActivate: [AuthGuard] },
      { path: 'posts/:id', component: PostDetails, canActivate: [AuthGuard] },
      { path: 'posts', component: Posts, canActivate: [AuthGuard] },
      { path: 'create-post', component: PostCreate, canActivate: [AuthGuard] },
      { path: 'topics', component: Topics, canActivate: [AuthGuard] },
      { path: '', redirectTo: '/connect', pathMatch: 'full' },
      { path: '**', component: NotFound },
    ],
  },
];
