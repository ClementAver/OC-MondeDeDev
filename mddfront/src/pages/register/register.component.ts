import { Component } from '@angular/core';
import { RegisterForm } from '../../features/authentication/ui/registerForm/registerForm.component';
import { BackButton } from '../../widgets/backButton/ui/backButton.component';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'register',
  standalone: true,
  imports: [RegisterForm, BackButton, RouterModule],
  providers: [Router],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class Register {}
