import { Component } from '@angular/core';
import { CustomButton } from '../../../../shared/button/button.component';
import { CustomInput } from '../../../../shared/input/input.component';
import { ReactiveFormsModule, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'login-form',
  standalone: true,
  imports: [ReactiveFormsModule, CustomInput, CustomButton],
  templateUrl: './loginForm.component.html',
  styleUrls: ['./loginForm.component.scss'],
})
export class LoginForm {
  loginForm = new FormGroup({
    identifier: new FormControl(''),
    password: new FormControl(''),
  });

  handleSubmit() {
    alert(this.loginForm.value.identifier);
  }
}
