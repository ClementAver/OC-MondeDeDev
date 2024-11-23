import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  ReactiveFormsModule,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { CustomInput } from '../../../../shared/input/input.component';
import { CustomButton } from '../../../../shared/button/button.component';
import { ProfileService } from '../../api/ProfileService';
import { AuthenticationService } from '../../../../features/authentication/api/AuthenticationService';
import { MeResponse } from '../../../../features/authentication/model/MeResponse.interface';

@Component({
  selector: 'update-user-form',
  standalone: true,
  imports: [ReactiveFormsModule, CustomInput, CustomButton, CommonModule],
  templateUrl: './update-user-form.component.html',
  styleUrl: './update-user-form.component.scss',
})
export class UpdateUserFormComponent {
  constructor(
    private authenticationService: AuthenticationService,
    private profileService: ProfileService
  ) {}

  private me: MeResponse | undefined = undefined;
  ngOnInit() {
    this.authenticationService.me().subscribe({
      next: (response) => {
        console.log(response);

        this.me = response;
        this.updateUserForm.get('username')?.setValue(response.name);
        this.updateUserForm.get('email')?.setValue(response.email);
      },
    });
  }

  mailRegex = new RegExp('^[\\w\\-.]+@([\\w\\-]+\\.)+[\\w\\-]{2,4}$');

  updateUserForm = new FormGroup({
    username: new FormControl('', [
      Validators.required,
      Validators.maxLength(256),
    ]),
    email: new FormControl('', [
      Validators.required,
      Validators.pattern(this.mailRegex),
    ]),
  });

  get id() {
    return this.me?.id;
  }

  get username() {
    return this.updateUserForm.get('username');
  }

  get email() {
    return this.updateUserForm.get('email');
  }

  handleSubmit() {
    const requestBody = {
      name: this.updateUserForm.value.username as string,
      email: this.updateUserForm.value.email as string,
    };

    this.profileService.updateUser(this.id as number, requestBody).subscribe({
      next: (response) => {
        this.updateUserForm.get('username')?.setValue(response.name);
        this.updateUserForm.get('email')?.setValue(response.email);
      },
      error: (error: any) => {
        console.error('Login failed', error);
        alert(error.message);
      },
    });
  }
}