import { Component } from '@angular/core';
import { CustomButton } from '../../../../shared/button/button.component';
import { CustomInput } from '../../../../shared/input/input.component';
import { ReactiveFormsModule, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'register-form',
  standalone: true,
  imports: [ReactiveFormsModule, CustomInput, CustomButton],
  templateUrl: './registerForm.component.html',
  styleUrls: ['./registerForm.component.scss'],
})
export class RegisterForm {
  registerForm = new FormGroup({
    username: new FormControl(''),
    email: new FormControl(''),
    password: new FormControl(''),
  });

  handleSubmit() {
    alert(this.registerForm.value.email);
  }
}
