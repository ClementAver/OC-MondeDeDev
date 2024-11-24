import { Component } from '@angular/core';
import { UpdateUserFormComponent } from './components/update-user-form/update-user-form.component';
import { CustomButton } from '../../shared/button/button.component';
import { AuthenticationService } from '../../features/authentication/api/AuthenticationService';

@Component({
  selector: 'profile',
  standalone: true,
  imports: [UpdateUserFormComponent, CustomButton],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss',
})
export class Profile {
  constructor(private authenticationService: AuthenticationService) {}
  handleLogout() {
    this.authenticationService.logout();
  }
}
