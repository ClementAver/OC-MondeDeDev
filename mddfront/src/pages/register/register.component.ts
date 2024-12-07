import { Component } from '@angular/core';
import { RegisterForm } from '../../features/authentication/ui/registerForm/register-form.component';
import { BackButton } from '../../widgets/backButton/ui/backButton.component';

@Component({
  selector: 'register',
  standalone: true,
  imports: [RegisterForm, BackButton],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class Register {}
