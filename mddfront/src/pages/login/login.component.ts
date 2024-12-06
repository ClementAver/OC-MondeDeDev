import { Component } from '@angular/core';
import { LoginForm } from '../../features/authentication/ui/loginForm/login-form.component';
import { BackButton } from '../../widgets/backButton/ui/backButton.component';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'login',
  standalone: true,
  imports: [LoginForm, BackButton, RouterModule],
  providers: [Router],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class Login {}
