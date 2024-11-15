import { Component } from '@angular/core';
import { LoginForm } from '../../features/authentication/ui/loginForm/loginForm.component';

@Component({
  selector: 'login',
  standalone: true,
  imports: [LoginForm],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class Login {}
